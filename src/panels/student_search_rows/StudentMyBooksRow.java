package panels.student_search_rows;

import database.Book;

import javax.swing.*;
import java.awt.*;

public class StudentMyBooksRow extends JPanel {
    private JLabel bName;
    private JLabel writer;
    private JLabel type;
    private JButton returnButton;
    private Book book;

    private int bookId;

    public StudentMyBooksRow(Book book) {
        this.book= book;
        this.setLayout(new GridLayout());

        Dimension rowSize = new Dimension(500, 30);
        this.setPreferredSize(rowSize);
        this.setMaximumSize(rowSize);
        this.setMinimumSize(rowSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        Dimension fixedSize = new Dimension(50, 25);

        bName = new JLabel(book.getBookName());
        bName.setPreferredSize(fixedSize);
        this.add(bName, gbc);

        gbc.gridx = 1;
        writer = new JLabel(book.getBookWriter());
        writer.setPreferredSize(fixedSize);
        this.add(writer, gbc);

        gbc.gridx = 2;
        type = new JLabel(book.getBookType());
        type.setPreferredSize(fixedSize);
        this.add(type, gbc);

        gbc.gridx = 3;
        returnButton = new JButton("RETURN");
        returnButton.setPreferredSize(fixedSize);
        this.add(returnButton, gbc);

        bookId= book.getBookId();
    }

    public JButton getReturnButton() {
        return returnButton;
    }
    public int getBookId() {
        return bookId;
    }

    public Book getBook() {
        return book;
    }
}
