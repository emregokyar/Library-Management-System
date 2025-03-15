package panels.admin_search_rows;

import database.Student;

import javax.swing.*;
import java.awt.*;

public class AdminStudentRow extends JPanel {
    private JLabel nameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel ownedBooksLabel;
    private JButton deleteStudentButton;
    private int studentId;

    public AdminStudentRow(Student student) {
        this.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fixedSize = new Dimension(50, 25);

        nameLabel = new JLabel(student.getStudentName());
        nameLabel.setPreferredSize(fixedSize);
        this.add(nameLabel, gbc);

        gbc.gridx = 1;
        lastNameLabel = new JLabel(student.getStudentLastname());
        lastNameLabel.setPreferredSize(fixedSize);
        this.add(lastNameLabel, gbc);

        fixedSize = new Dimension(170, 25);
        gbc.gridx = 2;
        emailLabel = new JLabel(student.getStudentEmail());
        emailLabel.setPreferredSize(fixedSize);
        this.add(emailLabel, gbc);

        gbc.gridx = 3;
        StringBuilder books = new StringBuilder();
        if (student.getOwnedBooks() != null) {
            student.getOwnedBooks().forEach(b -> books.append(b.getBookName()).append(", "));
            if (books.length() > 0) {
                books.setLength(books.length() - 2);
                ownedBooksLabel = new JLabel(books.toString());
            }else {
                ownedBooksLabel = new JLabel("No Owned Books");
            }
        }else {
            ownedBooksLabel= new JLabel("No Owned Books");
        }
        ownedBooksLabel.setPreferredSize(fixedSize);
        this.add(ownedBooksLabel, gbc);

        gbc.gridx = 4;
        gbc.anchor= GridBagConstraints.EAST;
        deleteStudentButton = new JButton("DELETE");
        deleteStudentButton.setSize(new Dimension(50, 30));
        this.add(deleteStudentButton, gbc);

        studentId = student.getStudentId();
    }

    public JButton getDeleteStudentButton() {
        return deleteStudentButton;
    }

    public int getStudentId() {
        return studentId;
    }
}
