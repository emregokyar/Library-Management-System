package panels.admin_panels;

import javax.swing.*;
import java.awt.*;

public class AdminMainPanel extends JPanel {
    private JButton searchButton;
    private JButton addBookButton;
    private JButton adminInfoButton;
    private JButton quitButton;
    private JButton deleteUserButton;

    public AdminMainPanel() {
        this.setPreferredSize(new Dimension(100, 400));
        this.setBackground(Color.lightGray);

        searchButton = new JButton("SEARCH BOOK");
        searchButton.setPreferredSize(new Dimension(200, 40));
        addBookButton = new JButton("ADD BOOK");
        addBookButton.setPreferredSize(new Dimension(200, 40));
        deleteUserButton= new JButton("SEARCH USER");
        deleteUserButton.setPreferredSize(new Dimension(200,40));
        AdminTop topPart = new AdminTop(searchButton, addBookButton, deleteUserButton);
        this.add(topPart, BorderLayout.NORTH);

        adminInfoButton = new JButton("ADMIN");
        adminInfoButton.setPreferredSize(new Dimension(200,40));
        quitButton = new JButton("QUIT");
        quitButton.setPreferredSize(new Dimension(200,20));
        AdminBottom bottomPart= new AdminBottom(adminInfoButton, quitButton);
        this.add(bottomPart, BorderLayout.SOUTH);
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAddBookButton() {
        return addBookButton;
    }

    public JButton getAdminInfoButton() {
        return adminInfoButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JButton getDeleteUserButton() {
        return deleteUserButton;
    }
}

class AdminBottom extends JPanel {
    public AdminBottom(JButton admin, JButton quit) {
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(Color.LIGHT_GRAY);

        this.add(admin, BorderLayout.CENTER);
        this.add(quit, BorderLayout.CENTER);
    }
}

class AdminTop extends JPanel {
    public AdminTop(JButton search, JButton addBook, JButton delete) {
        this.setPreferredSize(new Dimension(100, 280));
        this.setBackground(Color.LIGHT_GRAY);

        this.add(search, BorderLayout.CENTER);
        this.add(addBook, BorderLayout.CENTER);
        this.add(delete, BorderLayout.CENTER);
    }
}
