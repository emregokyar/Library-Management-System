package panels.entry_panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPanel extends JPanel {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private JTextField nameField;
    private JTextField lastnameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField passwordConfirmField;
    private JButton submitButton;
    private JButton goBackButton;

    public SignUpPanel() {
        this.setPreferredSize(new Dimension(700,400));
        this.setBackground(Color.lightGray);

        nameField = new JTextField("name");
        nameField.setPreferredSize(new Dimension(250,45));
        nameField.setForeground(Color.GRAY);
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("name")){
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()){
                    nameField.setForeground(Color.GRAY);
                    nameField.setText("name");
                }
            }
        });
        this.add(nameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        lastnameField = new JTextField("lastname");
        lastnameField.setPreferredSize(new Dimension(250,45));
        lastnameField.setForeground(Color.GRAY);
        lastnameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lastnameField.getText().equals("lastname")){
                    lastnameField.setText("");
                    lastnameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lastnameField.getText().isEmpty()){
                    lastnameField.setForeground(Color.GRAY);
                    lastnameField.setText("lastname");
                }
            }
        });
        this.add(lastnameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        emailField = new JTextField("email");
        emailField.setPreferredSize(new Dimension(250,45));
        emailField.setForeground(Color.GRAY);
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("email")){
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()){
                    emailField.setText("email");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
        this.add(emailField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        passwordField = new JTextField("password");
        passwordField.setPreferredSize(new Dimension(250,45));
        passwordField.setForeground(Color.GRAY);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("password")){
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()){
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("password");
                }
            }
        });
        this.add(passwordField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        passwordConfirmField = new JTextField("confirm password");
        passwordConfirmField.setPreferredSize(new Dimension(250,45));
        passwordConfirmField.setForeground(Color.GRAY);
        passwordConfirmField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordConfirmField.getText().equals("confirm password")){
                    passwordConfirmField.setText("");
                    passwordConfirmField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordConfirmField.getText().isEmpty()){
                    passwordConfirmField.setForeground(Color.GRAY);
                    passwordConfirmField.setText("confirm password");
                }
            }
        });
        this.add(passwordConfirmField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        submitButton = new JButton("SUBMIT");
        submitButton.setPreferredSize(new Dimension(250,20));
        this.add(submitButton);
        this.add(Box.createHorizontalStrut(700));

        goBackButton = new JButton("BACK");
        goBackButton.setPreferredSize(new Dimension(250,20));
        this.add(goBackButton);
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

    public JTextField getPasswordConfirmField() {
        return passwordConfirmField;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
