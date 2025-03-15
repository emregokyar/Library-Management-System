package panels.admin_panels;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminInfoPanel extends JPanel {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private JTextField nameField;
    private JTextField lastnameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton updateButton;

    public AdminInfoPanel() {
        this.setPreferredSize(new Dimension(600,400));
        this.setBackground(Color.WHITE);

        nameField = new JTextField("name");
        nameField.setPreferredSize(new Dimension(250,45));
        nameField.setBorder(BorderFactory.createEtchedBorder());
        this.add(nameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        lastnameField = new JTextField("lastname");
        lastnameField.setPreferredSize(new Dimension(250,45));
        lastnameField.setBorder(BorderFactory.createEtchedBorder());
        this.add(lastnameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        emailField = new JTextField("email");
        emailField.setPreferredSize(new Dimension(250,45));
        emailField.setBorder(BorderFactory.createEtchedBorder());
        this.add(emailField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        passwordField = new JTextField("password");
        passwordField.setPreferredSize(new Dimension(250,45));
        passwordField.setBorder(BorderFactory.createEtchedBorder());
        this.add(passwordField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        updateButton = new JButton("UPDATE");
        updateButton.setPreferredSize(new Dimension(250,20));
        this.add(updateButton);
        this.add(Box.createHorizontalStrut(700));
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getLastnameField() {
        return lastnameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }
    public JButton getUpdateButton() {
        return updateButton;
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
