package panels.student_search_rows;

import database.Book;

import javax.swing.*;
import java.awt.*;

public class StudentBookSearchRow extends JPanel {
    private JLabel bookName;
    private JLabel bookWriter;
    private JLabel bookType;
    private JLabel availability;
    private JButton getButton;

    private int bookId;

    public StudentBookSearchRow(Book book) {
        this.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fixedSize = new Dimension(50, 25);

        bookName = new JLabel(book.getBookName());
        bookName.setPreferredSize(fixedSize);
        this.add(bookName, gbc);

        gbc.gridx = 1;
        bookWriter = new JLabel(book.getBookWriter());
        bookWriter.setPreferredSize(fixedSize);
        this.add(bookWriter, gbc);

        gbc.gridx = 2;
        bookType = new JLabel(book.getBookType());
        bookType.setPreferredSize(fixedSize);
        this.add(bookType, gbc);

        gbc.gridx = 3;
        if (!book.isIssued()) {
            availability = new JLabel("AVAILABLE");
        }else {
            availability = new JLabel("NOT AVAILABLE");
        }
        availability.setPreferredSize(fixedSize);
        this.add(availability, gbc);

        gbc.gridx = 4;
        getButton = new JButton("GET");
        this.add(getButton, gbc);

        bookId= book.getBookId();
    }
    public JLabel getAvailability() {
        return availability;
    }

    public JButton getGetButton() {
        return getButton;
    }

    public int getBookId() {
        return bookId;
    }
}
