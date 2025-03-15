package panels.student_panels;

import javax.swing.*;
import java.awt.*;

public class StudentMainPanel extends JPanel {
    private JButton borrowedBooksButton;
    private JButton searchButton;
    private JButton userInfoButton;
    private JButton quitButton;

    public StudentMainPanel() {
        this.setPreferredSize(new Dimension(100,400));
        this.setBackground(Color.lightGray);

        borrowedBooksButton = new JButton("MY BOOKS");
        borrowedBooksButton.setPreferredSize(new Dimension(200,40));
        searchButton = new JButton("SEARCH");
        searchButton.setPreferredSize(new Dimension(200,40));
        Top topPanel= new Top(borrowedBooksButton, searchButton );
        this.add(topPanel, BorderLayout.NORTH);

        userInfoButton = new JButton("USER");
        userInfoButton.setPreferredSize(new Dimension(200,40));
        quitButton = new JButton("QUIT");
        quitButton.setPreferredSize(new Dimension(200,20));
        Bottom bottom= new Bottom(userInfoButton, quitButton);
        this.add(bottom, BorderLayout.SOUTH);
    }

    public JButton getBorrowedBooksButton() {
        return borrowedBooksButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getUserInfoButton() {
        return userInfoButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }
}

class Bottom extends JPanel {
    public Bottom(JButton user, JButton quit) {
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(Color.LIGHT_GRAY);

        this.add(user, BorderLayout.CENTER);
        this.add(quit, BorderLayout.CENTER);

    }
}

class Top extends JPanel {
    public Top(JButton books, JButton search) {
        this.setPreferredSize(new Dimension(100, 280));
        this.setBackground(Color.LIGHT_GRAY);
        this.add(books, BorderLayout.CENTER);
        this.add(search, BorderLayout.CENTER);
    }
}
