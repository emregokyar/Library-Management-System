package database;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_writer")
    private String bookWriter;

    @Column(name = "book_type")
    private String bookType;

    @Column(name = "is_issued", nullable = false)
    private boolean isIssued = false;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student owner;

    public Book() {
    }

    public Book(String bookName, String bookWriter, String bookType) {
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookType = bookType;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public Student getOwner() {
        return owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;
    }

    public void releaseBook(Student owner) {
        owner= null;
        setOwner(owner);
        setIssued(false);
    }
}


