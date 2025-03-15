package panels.admin_search_rows;

import database.Book;

import javax.swing.*;
import java.awt.*;

public class AdminBooksRow extends JPanel {
    private JTextField bookName;
    private JTextField bookWriter;
    private JTextField bookType;
    private JLabel isIssued;
    private JButton updateButton;
    private JButton deleteBookButton;
    private int bookId;

    public AdminBooksRow(Book book) {
        this.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fixedSize = new Dimension(50, 25);


        bookName = new JTextField(book.getBookName());
        bookName.setPreferredSize(fixedSize);
        this.add(bookName, gbc);

        gbc.gridx = 1;
        bookWriter = new JTextField(book.getBookWriter());
        bookWriter.setPreferredSize(fixedSize);
        this.add(bookWriter, gbc);

        gbc.gridx = 2;
        bookType = new JTextField(book.getBookType());
        bookType.setPreferredSize(fixedSize);
        this.add(bookType, gbc);

        gbc.gridx = 3;
        if (!book.isIssued()) {
            isIssued = new JLabel("AVAILABLE");
        } else {
            isIssued = new JLabel("NOT AVAILABLE");
        }
        isIssued.setPreferredSize(fixedSize);
        this.add(isIssued, gbc);

        gbc.gridx = 4;
        updateButton = new JButton("UPDATE");
        this.add(updateButton, gbc);

        gbc.gridx = 5;
        deleteBookButton = new JButton("DELETE");
        deleteBookButton.setPreferredSize(fixedSize);
        this.add(deleteBookButton, gbc);

        bookId = book.getBookId();
    }

    public JTextField getBookName() {
        return bookName;
    }
    public JTextField getBookWriter() {
        return bookWriter;
    }

    public JTextField getBookType() {
        return bookType;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
    public JButton getDeleteBookButton() {
        return deleteBookButton;
    }

    public int getBookId() {
        return bookId;
    }
}
