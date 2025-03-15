package frame;

import database.Admin;
import database.Database;
import database.Student;
import panels.admin_panels.*;
import panels.entry_panels.AdminEntrancePanel;
import panels.entry_panels.EntrancePanel;
import panels.entry_panels.SignUpPanel;
import panels.student_panels.StudentBooksPanel;
import panels.student_panels.StudentInfoPanel;
import panels.student_panels.StudentMainPanel;
import panels.student_panels.StudentSearchPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class AppFrame extends JFrame {
    private EntrancePanel entrancePanel;
    private SignUpPanel signUpPanel;
    private AdminEntrancePanel adminEntrancePanel;
    private StudentMainPanel studentMainPanel;
    private StudentBooksPanel studentBooksPanel;
    private StudentSearchPanel studentSearchPanel;
    private StudentInfoPanel studentInfoPanel;
    private AdminMainPanel adminMainPanel;
    private AdminSearchPanel adminSearchPanel;
    private UserSearchPanel userSearchPanel;    
    private AdminInfoPanel adminInfoPanel;
    private AdminAddBook adminAddBookPanel;
    private final Database database = new Database();

    private Student student;
    private Admin admin;

    public AppFrame() throws HeadlessException {
        this.setSize(new Dimension(700, 400));
        this.setName("WELCOME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        entrancePanel = new EntrancePanel();
        signUpPanel = new SignUpPanel();
        adminEntrancePanel = new AdminEntrancePanel();
        studentMainPanel = new StudentMainPanel();
        studentInfoPanel = new StudentInfoPanel();
        adminMainPanel = new AdminMainPanel();
        adminSearchPanel = new AdminSearchPanel(database);
        userSearchPanel = new UserSearchPanel(database);
        adminInfoPanel = new AdminInfoPanel();
        adminAddBookPanel = new AdminAddBook();
        admin = null;
        student = null;
    }

    public void activateEntrancePage() {
        entrancePanel.getSubmit().removeMouseListener(entrancePanel.getSubmit().getMouseListeners()[0]);
        removeAllListeners(entrancePanel.getSubmit());
        removeAllListeners(entrancePanel.getUserPass());

        var userId = entrancePanel.getUserField();
        var userPass = entrancePanel.getUserPass();
        userPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (database.checkEntranceInfo(userId.getText(), userPass.getText())) {
                    student = database.checkStudentIfExist(userId.getText());
                    AppFrame.this.getContentPane().removeAll();
                    userId.setText("username");
                    userId.setForeground(Color.GRAY);
                    userPass.setText("password");
                    userPass.setForeground(Color.GRAY);
                    studentBooksPanel = new StudentBooksPanel(database, student);
                    studentSearchPanel = new StudentSearchPanel(database, student);
                    activateStudentPanel();
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Wrong Password or UserId!!");
                }
                revalidate();
                repaint();
            }
        });

        var submit = entrancePanel.getSubmit();
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (database.checkEntranceInfo(userId.getText(), userPass.getText())) {
                    student = database.checkStudentIfExist(userId.getText());
                    AppFrame.this.getContentPane().removeAll();
                    userId.setText("username");
                    userId.setForeground(Color.GRAY);
                    userPass.setText("password");
                    userPass.setForeground(Color.GRAY);
                    studentBooksPanel = new StudentBooksPanel(database, student);
                    studentSearchPanel = new StudentSearchPanel(database, student);
                    activateStudentPanel();
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Wrong Password or UserId!!");
                }
                revalidate();
                repaint();
            }
        });

        var admin = entrancePanel.getAdminEntry();
        admin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFrame.this.getContentPane().removeAll();
                activateAdminEntrancePage();
                revalidate();
                repaint();
            }
        });

        var signUp = entrancePanel.getSignup();
        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFrame.this.getContentPane().removeAll();
                activateSignUpPage();
                revalidate();
                repaint();
            }
        });
        this.add(entrancePanel);
        validate();
    }

    public void activateSignUpPage() {
        signUpPanel.getSubmitButton().removeMouseListener(signUpPanel.getSubmitButton().getMouseListeners()[0]);
        removeAllListeners(signUpPanel.getSubmitButton());

        var name = signUpPanel.getNameField();
        var lastname = signUpPanel.getLastnameField();
        var email = signUpPanel.getEmailField();
        var pass = signUpPanel.getPasswordField();
        var passConfirm = signUpPanel.getPasswordConfirmField();
        passConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean check = !signUpPanel.getPasswordField().getText().isBlank() &&
                        !signUpPanel.getNameField().getText().isBlank() &&
                        !signUpPanel.getLastnameField().getText().isBlank();

                boolean secondCheck = !signUpPanel.getPasswordField().getText().equals("password") &&
                        !signUpPanel.getLastnameField().getText().equals("lastname") &&
                        !signUpPanel.getNameField().getText().equals("name");

                if (signUpPanel.isValidEmail(email.getText()) && check && secondCheck) {
                    if (Objects.equals(pass.getText(), passConfirm.getText())) {
                        if (database.addStudent(name.getText(), lastname.getText(), email.getText(), pass.getText())) {
                            userSearchPanel.updateSearchPanel();
                            JOptionPane.showMessageDialog(AppFrame.this, "SUCCESS!!");
                            name.setText("name");
                            name.setForeground(Color.GRAY);
                            lastname.setText("last name");
                            lastname.setForeground(Color.GRAY);
                            email.setText("email");
                            email.setForeground(Color.GRAY);
                            pass.setText("password");
                            pass.setForeground(Color.GRAY);
                            passConfirm.setText("confirm password");
                            passConfirm.setForeground(Color.GRAY);
                        } else {
                            JOptionPane.showMessageDialog(AppFrame.this, "Something went wrong, Try again!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Check your info!");
                }
                revalidate();
                repaint();
            }
        });

        var submitButton = signUpPanel.getSubmitButton();
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean check = !signUpPanel.getPasswordField().getText().isBlank() &&
                        !signUpPanel.getNameField().getText().isBlank() &&
                        !signUpPanel.getLastnameField().getText().isBlank();

                boolean secondCheck = !signUpPanel.getPasswordField().getText().equals("password") &&
                        !signUpPanel.getLastnameField().getText().equals("lastname") &&
                        !signUpPanel.getNameField().getText().equals("name");

                if (signUpPanel.isValidEmail(email.getText()) && check && secondCheck) {
                    if (Objects.equals(pass.getText(), passConfirm.getText())) {
                        if (database.addStudent(name.getText(), lastname.getText(), email.getText(), pass.getText())) {
                            userSearchPanel.updateSearchPanel();
                            JOptionPane.showMessageDialog(AppFrame.this, "SUCCESS!!");
                            name.setText("name");
                            name.setForeground(Color.GRAY);
                            lastname.setText("last name");
                            lastname.setForeground(Color.GRAY);
                            email.setText("email");
                            email.setForeground(Color.GRAY);
                            pass.setText("password");
                            pass.setForeground(Color.GRAY);
                            passConfirm.setText("confirm password");
                            passConfirm.setForeground(Color.GRAY);
                        } else {
                            JOptionPane.showMessageDialog(AppFrame.this, "Something went wrong, Try again!!");
                            revalidate();
                            repaint();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Check your info!");
                    revalidate();
                    repaint();
                }
                revalidate();
                repaint();
            }
        });

        var back = signUpPanel.getGoBackButton();
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFrame.this.getContentPane().removeAll();
                activateEntrancePage();
                revalidate();
                repaint();
            }
        });
        this.add(signUpPanel);
        this.validate();
    }

    public void activateAdminEntrancePage() {

        System.out.println("Directing Admin Activate Page");
        adminEntrancePanel.getSubmit().removeMouseListener(adminEntrancePanel.getSubmit().getMouseListeners()[0]);
        adminEntrancePanel.getGoBackButton().removeMouseListener(adminEntrancePanel.getGoBackButton().getMouseListeners()[0]);
        //        removeAllListeners(adminEntrancePanel.getSubmit());
        //      removeAllListeners(adminEntrancePanel.getGoBackButton());

        var userId = adminEntrancePanel.getUserField();
        var userPass = adminEntrancePanel.getUserPass();
        userPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (database.checkAdmin(userId.getText(), userPass.getText())) {
                    admin = database.getAdmin(userId.getText());
                    AppFrame.this.getContentPane().removeAll();
                    activateAdminPanel();
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Wrong Admin Id or Password!!");
                }
                userId.setText("username");
                userId.setForeground(Color.GRAY);
                userPass.setText("password");
                userPass.setForeground(Color.GRAY);
                revalidate();
                repaint();
            }
        });

        var submit = adminEntrancePanel.getSubmit();
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (database.checkAdmin(userId.getText(), userPass.getText())) {
                    admin = database.getAdmin(userId.getText());
                    AppFrame.this.getContentPane().removeAll();
                    activateAdminPanel();
                } else {
                    JOptionPane.showMessageDialog(AppFrame.this, "Wrong Admin Id or Password!!");
                }
                userId.setText("username");
                userId.setForeground(Color.GRAY);
                userPass.setText("password");
                userPass.setForeground(Color.GRAY);
                revalidate();
                repaint();
            }
        });

        var back = adminEntrancePanel.getGoBackButton();
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AppFrame.this.getContentPane().removeAll();
                activateEntrancePage();
                revalidate();
                repaint();
            }
        });

        this.add(adminEntrancePanel);
        this.validate();
    }

    public void activateStudentPanel() {
        System.out.println("Directing Student Page");
        var borrowed = studentMainPanel.getBorrowedBooksButton();
        borrowed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(studentBooksPanel);
            }
        });

        var search = studentMainPanel.getSearchButton();
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(studentSearchPanel);
            }
        });

        var user = studentMainPanel.getUserInfoButton();
        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(studentInfoPanel);

                studentInfoPanel.getUpdateButton().removeMouseListener(studentInfoPanel.getUpdateButton().getMouseListeners()[0]);
                studentInfoPanel.getDeleteButton().removeMouseListener(studentInfoPanel.getDeleteButton().getMouseListeners()[0]);

                studentInfoPanel.getNameField().setText(student.getStudentName());
                studentInfoPanel.getLastnameField().setText(student.getStudentLastname());
                studentInfoPanel.getEmailField().setText(student.getStudentEmail());
                studentInfoPanel.getPasswordField().setText(student.getStudentPassword());

                boolean checkVal = !studentInfoPanel.getPasswordField().getText().isBlank() &&
                        !studentInfoPanel.getNameField().getText().isBlank() &&
                        !studentInfoPanel.getLastnameField().getText().isBlank();

                boolean checkAgain = !studentInfoPanel.getPasswordField().getText().equals("password") &&
                        !studentInfoPanel.getNameField().getText().equals("name") &&
                        !studentInfoPanel.getLastnameField().getText().equals("lastname");
                studentInfoPanel.getUpdateButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (studentInfoPanel.isValidEmail(studentInfoPanel.getEmailField().getText()) && checkVal && checkAgain) {
                            var success = database.studentUpdate(student,
                                    studentInfoPanel.getNameField().getText(),
                                    studentInfoPanel.getLastnameField().getText(),
                                    studentInfoPanel.getEmailField().getText(),
                                    studentInfoPanel.getPasswordField().getText()
                            );
                            if (!success) {
                                JOptionPane.showMessageDialog(AppFrame.this, "Successfully made changes");
                            } else {
                                JOptionPane.showMessageDialog(AppFrame.this, "Something went wrong");
                            }
                        } else {
                            JOptionPane.showMessageDialog(AppFrame.this, "Check your info");
                        }
                        revalidate();
                        repaint();
                    }
                });

                studentInfoPanel.getDeleteButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (student.getOwnedBooks().isEmpty()) {
                            if (JOptionPane.showConfirmDialog(AppFrame.this, "Are you sure?", "WARNING",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                database.deleteUser(student);
                                AppFrame.this.getContentPane().removeAll();
                                AppFrame.this.add(entrancePanel);
                                revalidate();
                                repaint();
                            }
                        } else {
                            JOptionPane.showMessageDialog(AppFrame.this, "You must return all your books!!");
                            revalidate();
                            repaint();
                        }
                    }
                });
            }
        });

        var quit = studentMainPanel.getQuitButton();
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                student = null;
                AppFrame.this.getContentPane().removeAll();
                AppFrame.this.add(entrancePanel);
                entrancePanel.getUserField().setText("username");
                entrancePanel.getUserField().setForeground(Color.GRAY);
                entrancePanel.getUserPass().setText("password");
                entrancePanel.getUserPass().setForeground(Color.GRAY);
                revalidate();
                repaint();
            }
        });

        this.add(studentMainPanel, BorderLayout.WEST);
        this.validate();
    }

    private void switchPanel(JPanel newPanel) {
        studentSearchPanel.updateBookPanel();
        studentSearchPanel.getSearchField().setText("Search Book");
        studentSearchPanel.getSearchField().setForeground(Color.GRAY);
        studentBooksPanel.updateMyBooksPanel();
        this.getContentPane().removeAll();
        this.add(studentMainPanel, BorderLayout.WEST);
        this.add(newPanel, BorderLayout.EAST);
        this.revalidate();
        this.repaint();
    }


    public void activateAdminPanel() {
        System.out.println("Directing Admin Page");
        var search = adminMainPanel.getSearchButton();
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchAdminPanel(adminSearchPanel);
            }
        });

        var addBook = adminMainPanel.getAddBookButton();
        addBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchAdminPanel(adminAddBookPanel);

                adminAddBookPanel.getSubmitButton().removeMouseListener(adminAddBookPanel.getSubmitButton().getMouseListeners()[0]);
                adminAddBookPanel.getSubmitButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (JOptionPane.showConfirmDialog(AppFrame.this, "Are you sure?", "ADD BOOK",
                                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                            if (!database.addBook(adminAddBookPanel.getBookNameField().getText(),
                                    adminAddBookPanel.getWriterNameField().getText(),
                                    adminAddBookPanel.getBookTypesSelection().getSelectedItem().toString())) {
                                JOptionPane.showConfirmDialog(AppFrame.this, "This Book Already Exists", "WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                            }
                            adminAddBookPanel.getBookNameField().setText("Book Name");
                            adminAddBookPanel.getBookNameField().setForeground(Color.GRAY);
                            adminAddBookPanel.getWriterNameField().setText("Writer Name");
                            adminAddBookPanel.getWriterNameField().setForeground(Color.GRAY);
                        }
                    }
                });
            }
        });

        var searchUser = adminMainPanel.getDeleteUserButton();
        searchUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchAdminPanel(userSearchPanel);
            }
        });

        var adminInfo = adminMainPanel.getAdminInfoButton();
        adminInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchAdminPanel(adminInfoPanel);
                adminInfoPanel.getNameField().setText(admin.getAdminName());
                adminInfoPanel.getLastnameField().setText(admin.getAdminLastName());
                adminInfoPanel.getEmailField().setText(admin.getAdminEmail());
                adminInfoPanel.getPasswordField().setText(admin.getAdminPass());

                adminInfoPanel.getUpdateButton().removeMouseListener(adminInfoPanel.getUpdateButton().getMouseListeners()[0]);
                adminInfoPanel.getUpdateButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        boolean check = !adminInfoPanel.getPasswordField().getText().isBlank() &&
                                !adminInfoPanel.getNameField().getText().isBlank() &&
                                !adminInfoPanel.getLastnameField().getText().isBlank();

                        boolean checkSecond = !adminInfoPanel.getPasswordField().getText().equals("password") &&
                                !adminInfoPanel.getNameField().getText().equals("name") &&
                                !adminInfoPanel.getLastnameField().getText().equals("lastname");

                        if (adminInfoPanel.isValidEmail(adminInfoPanel.getEmailField().getText()) && check && checkSecond) {
                            boolean success = database.adminUpdate(admin,
                                    adminInfoPanel.getNameField().getText(),
                                    adminInfoPanel.getLastnameField().getText(),
                                    adminInfoPanel.getEmailField().getText(),
                                    adminInfoPanel.getPasswordField().getText());
                            if (!success) {
                                JOptionPane.showMessageDialog(AppFrame.this, "SUCCESSFULLY MADE CHANGES");
                                revalidate();
                                repaint();
                            } else {
                                JOptionPane.showMessageDialog(AppFrame.this, "Something Went Wrong");
                                revalidate();
                                repaint();
                            }
                        } else {
                            JOptionPane.showMessageDialog(AppFrame.this, "Please enter a valid address or password");
                            revalidate();
                            repaint();
                        }
                        revalidate();
                        repaint();
                    }
                });
            }
        });

        var quit = adminMainPanel.getQuitButton();
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin = null;
                AppFrame.this.getContentPane().removeAll();
                AppFrame.this.add(entrancePanel);
                revalidate();
                repaint();
            }
        });

        this.add(adminMainPanel, BorderLayout.WEST);
        this.validate();
    }


    private void switchAdminPanel(JPanel newPanel) {
        userSearchPanel.updateSearchPanel();
        userSearchPanel.getSearchField().setText("Search User");
        userSearchPanel.getSearchField().setForeground(Color.GRAY);

        adminSearchPanel.updateBookPanel();
        adminSearchPanel.getSearchField().setText("Search Book");
        adminSearchPanel.getSearchField().setForeground(Color.GRAY);

        this.getContentPane().removeAll();
        this.add(adminMainPanel, BorderLayout.WEST);
        this.add(newPanel, BorderLayout.EAST);
        this.revalidate();
        this.repaint();
    }

    private void removeAllListeners(AbstractButton button) {
        for (MouseListener ml : button.getMouseListeners()) {
            button.removeMouseListener(ml);
        }
    }

    private void removeAllListeners(JTextField field) {
        for (ActionListener al : field.getActionListeners()) {
            field.removeActionListener(al);
        }
    }
}