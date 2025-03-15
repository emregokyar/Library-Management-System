package panels.entry_panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AdminEntrancePanel extends JPanel {
    private JTextField userField;
    private JTextField userPass;
    private JButton submit;
    private JButton goBackButton;

    public AdminEntrancePanel() {
        this.setPreferredSize(new Dimension(700,400));
        this.setBackground(Color.lightGray);

        userField= new JTextField("username");
        userField.setForeground(Color.GRAY);
        userField.setPreferredSize(new Dimension(250,45));
        userField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("username")){
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()){
                    userField.setForeground(Color.GRAY);
                    userField.setText("username");
                }
            }
        });
        this.add(userField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        userPass= new JTextField("password");
        userPass.setForeground(Color.GRAY);
        userPass.setPreferredSize(new Dimension(250,45));
        userPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userPass.getText().equals("password")){
                    userPass.setText("");
                    userPass.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userPass.getText().isEmpty()){
                    userPass.setForeground(Color.GRAY);
                    userPass.setText("password");
                }
            }
        });
        this.add(userPass, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        submit= new JButton("OK");
        submit.setPreferredSize(new Dimension(250,20));
        this.add(submit, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(700));

        goBackButton = new JButton("BACK");
        goBackButton.setPreferredSize(new Dimension(250,20));
        this.add(goBackButton);
    }

    public JTextField getUserField() {
        return userField;
    }

    public JTextField getUserPass() {
        return userPass;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }
}
