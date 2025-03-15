package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Database {
    private static final EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("dev.lpa.management");
    private static final int DATABASE_NOT_FOUND_ERR_CODE = 1049;
    private static final String USE_SCHEMA = "USE management";
    private String userName;
    private String password;

    public Database() {
        executeDatabaseCreation();
    }

    public void executeDatabaseCreation() {
        Properties props = new Properties();
        try {
            Path propertiesPath = Path.of("management.properties");
            if (!Files.exists(propertiesPath)) {
                throw new RuntimeException("Properties file not found: management.properties");
            }
            props.load(Files.newInputStream(propertiesPath, StandardOpenOption.READ));
            userName = props.getProperty("user");
            password = props.getProperty("password");

            var dataSource = new MysqlDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPortNumber(3306);

            System.out.println("Checking if schema is created in database...");
            try (Connection connection = dataSource.getConnection(userName, password)) {
                if (!checkSchema(connection)) {
                    System.out.println("Creating management schema...");
                    createDataBaseIfNoExist(connection);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkSchema(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(USE_SCHEMA);
        } catch (SQLException e) {
            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == DATABASE_NOT_FOUND_ERR_CODE) {
                System.out.println("Database does not exist...");
                return false;
            }
            e.printStackTrace();
            throw e;
        }
        System.out.println("Found management schema in database!");
        return true;
    }

    public static void createDataBaseIfNoExist(Connection conn) throws SQLException {
        String createSchema = "CREATE SCHEMA management;";
        String createStudentTable = """
                CREATE TABLE management.students (
                    student_id INT NOT NULL AUTO_INCREMENT,
                    student_name VARCHAR(150) NOT NULL,
                    student_lastname VARCHAR(150) NOT NULL,
                    student_email VARCHAR(150),
                    student_password VARCHAR(50),
                    PRIMARY KEY (student_id)
                );
                """;
        String createBookTable = """
                CREATE TABLE management.books (
                    book_id INT NOT NULL AUTO_INCREMENT,
                    book_name VARCHAR(250) NOT NULL,
                    book_writer VARCHAR(250) NOT NULL,
                    book_type VARCHAR(100),
                    student_id INT,
                    is_issued BIT,
                    PRIMARY KEY (book_id),
                    FOREIGN KEY (student_id) REFERENCES management.students(student_id)
                );""";

        String createAdminTable = """
                CREATE TABLE management.admins (
                    admin_id INT NOT NULL AUTO_INCREMENT,
                    admin_name VARCHAR(150) NOT NULL,
                    admin_lastname VARCHAR(150) NOT NULL,
                    admin_email VARCHAR(150),
                    admin_password VARCHAR(50),
                    PRIMARY KEY (admin_id)
                );""";

        String createAdmin = """
                INSERT INTO management.admins(admin_name, admin_lastname, admin_email, admin_password)
                      VALUES ('1', '1', '1', '1');""";

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate(createSchema);
            statement.executeUpdate(createStudentTable);
            statement.executeUpdate(createAdminTable);
            statement.executeUpdate(createBookTable);
            statement.executeUpdate(createAdmin);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean addStudent(String studentName, String studentLastname, String email, String password) {
        Student checking = checkStudentIfExist(email);

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            if (checking != null) {
                System.out.println("This User Already Exists");
                return false;
            } else {
                System.out.println("Creating new user");
                var transaction = entityManager.getTransaction();
                transaction.begin();
                Student student = new Student(studentName, studentLastname, email, password);
                entityManager.persist(student);
                transaction.commit();
            }
        } catch (Exception e) {
            System.out.println("Can not create a new user");
            return false;
        }
        return true;
    }

    public Student checkStudentIfExist(String email) {
        Student student = null;

        System.out.println("Checking if student exists");
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            student = entityManager.createQuery("SELECT s FROM Student s WHERE s.studentEmail = :email", Student.class).setParameter("email", email).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not find user with this email");
        }
        return student;
    }

    public List<Book> getOwnedBooks(Student student) {
        List<Book> ownedBooks = new ArrayList<>();

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Student control = entityManager.find(Student.class, student.getStudentId());
            if (control != null) {
                ownedBooks = control.getOwnedBooks();
            } else {
                System.out.println("Student not found in database");
            }

            transaction.commit();
        }
        return ownedBooks;
    }

    public void addOwnedBook(Student student, int bookId) {
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Book book = entityManager.find(Book.class, bookId);

            if (book != null && student != null) {
                book.setOwner(student);
                book.setIssued(true);
                entityManager.persist(book);
            } else {
                System.out.println("Student or Book not found in database");
            }
            transaction.commit();
        }
    }

    public void returnOwnedBook(Student student, int bookId) {
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Book book = entityManager.find(Book.class, bookId);
            if (book != null && student != null) {
                book.releaseBook(student);
                entityManager.persist(book);
            } else {
                System.out.println("Student or Book not found in the database");
            }
            transaction.commit();
        }
    }

    public boolean checkEntranceInfo(String email, String password) {
        Student student = checkStudentIfExist(email);
        if (student != null) {
            if (Objects.equals(student.getStudentPassword(), password)) {
                System.out.println("Success!!");
                return true;
            } else {
                System.out.println("Password is wrong");
            }
        }
        return false;
    }

    public List<Student> findStudentByName(String studentName) {
        List<Student> students = new ArrayList<>();

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
            Root<Student> studentRoot = criteriaQuery.from(Student.class);

            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(studentRoot.get("studentName")), "%" + studentName + "%");
            criteriaQuery.select(studentRoot).where(namePredicate);

            Query query = entityManager.createQuery(criteriaQuery);
            students = query.getResultList();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public boolean addBook(String bookName, String writer, String bookType) {
        boolean found = false;
        var books = getBooksByName(bookName);

        for (var book : books) {
            if (book != null && Objects.equals(book.getBookName(), bookName) && Objects.equals(book.getBookWriter(), writer)) {
                found = true;
                break;
            }
        }
        if (!found) {
            try (EntityManager entityManager = sessionFactory.createEntityManager()) {
                System.out.println("Creating new Book");
                var transaction = entityManager.getTransaction();
                transaction.begin();
                Book newBook = new Book(bookName, writer, bookType);
                entityManager.persist(newBook);
                transaction.commit();
            } catch (Exception e) {
                System.out.println("Can not create a new book");
                return false;
            }
        }
        return !found;
    }

    public List<Book> getBooksByName(String bookName) {
        List<Book> books = new ArrayList<>();

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> bookRoot = criteriaQuery.from(Book.class);

            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(bookRoot.get("bookName")), "%" + bookName + "%");
            criteriaQuery.select(bookRoot).where(namePredicate);

            Query query = entityManager.createQuery(criteriaQuery);
            books = query.getResultList();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    public boolean checkAdmin(String email, String password) {
        Admin admin = null;
        boolean check = false;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            admin = entityManager.createQuery("SELECT a FROM Admin a WHERE a.adminEmail = :email", Admin.class).setParameter("email", email).getSingleResult();
            if (admin != null && Objects.equals(admin.getAdminEmail(), email) && Objects.equals(admin.getAdminPass(), password)) {
                System.out.println("Successfully entered your info");
                check = true;
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not find user with this email");
            e.printStackTrace();
        }
        return check;
    }

    public boolean studentUpdate(Student student, String studentName, String lastName, String email, String password) {

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            if (student != null) {
                student.setStudentName(studentName);
                student.setStudentLastname(lastName);
                student.setStudentEmail(email);
                student.setStudentPassword(password);
                entityManager.merge(student);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not update this user");
            e.printStackTrace();
        }
        return false;
    }

    public boolean adminUpdate(Admin admin, String adminName, String lastName, String email, String password) {

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            if (admin != null) {
                admin.setAdminName(adminName);
                admin.setAdminLastName(lastName);
                admin.setAdminEmail(email);
                admin.setAdminPass(password);
                entityManager.merge(admin);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not update admin info");
            e.printStackTrace();
        }
        return false;
    }

    public boolean bookUpdate(int bookId, String bookName, String writerName, String bookType) {

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            var book = entityManager.find(Book.class, bookId);

            if (book != null) {
                book.setBookName(bookName);
                book.setBookWriter(writerName);
                book.setBookType(bookType);
                entityManager.merge(book);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not update this book");
            e.printStackTrace();
        }
        return false;
    }

    public Admin getAdmin(String email) {
        Admin admin = null;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();
            admin = entityManager.createQuery("SELECT a FROM Admin a WHERE a.adminEmail = :email", Admin.class).setParameter("email", email).getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not find user with this email");
            e.printStackTrace();
        }
        return admin;
    }

    public boolean deleteUser(Student student) {

        boolean result = false;
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Student control = entityManager.find(Student.class, student.getStudentId());
            if (control != null) {
                entityManager.remove(control);
                transaction.commit();
                result = true;
            } else {
                System.out.println("Student not found in database");
            }

        } catch (Exception e) {
            System.out.println("Can not delete this user");
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> listAllBooks() {
        List<Book> books = null;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Can not find books");
        }

        return books;
    }

    public List<Student> listAllStudents() {
        List<Student> students = null;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
            transaction.commit();
        }
        return students;
    }

    public boolean deleteStudent(int studentId) {
        boolean success = true;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Student student = entityManager.find(Student.class, studentId);
            entityManager.remove(student);
            transaction.commit();
        } catch (Exception e) {
            success = false;
            System.out.println("Can not delete a user " + e.getMessage());
        }
        return success;
    }

    public boolean deleteBook(int bookId) {
        boolean success = true;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            Book book = entityManager.find(Book.class, bookId);
            entityManager.remove(book);
            transaction.commit();
        } catch (Exception e) {
            success = false;
            System.out.println("Can not remove a book" + e.getMessage());
        }
        return success;
    }
}