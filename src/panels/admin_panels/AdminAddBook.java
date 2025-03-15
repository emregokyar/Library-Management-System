package panels.admin_panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AdminAddBook extends JPanel {
    private JTextField bookNameField;
    private JTextField writerNameField;
    private JButton submitButton;

    private JComboBox<String> bookTypesSelection;

    public AdminAddBook() {
        this.setPreferredSize(new Dimension(600, 400));
        this.setBackground(Color.WHITE);

        bookNameField = new JTextField("Book Name");
        bookNameField.setPreferredSize(new Dimension(250, 45));
        bookNameField.setForeground(Color.GRAY);
        bookNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bookNameField.getText().equals("Book Name")) {
                    bookNameField.setText("");
                    bookNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (bookNameField.getText().isEmpty()) {
                    bookNameField.setForeground(Color.GRAY);
                    bookNameField.setText("Book Name");
                }
            }
        });
        bookNameField.setBorder(BorderFactory.createEtchedBorder());
        this.add(bookNameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        writerNameField = new JTextField("Writer Name");
        writerNameField.setPreferredSize(new Dimension(250, 45));
        writerNameField.setForeground(Color.GRAY);
        writerNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (writerNameField.getText().equals("Writer Name")){
                    writerNameField.setText("");
                    writerNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (writerNameField.getText().isEmpty()){
                    writerNameField.setForeground(Color.GRAY);
                    writerNameField.setText("Writer Name");
                }
            }
        });
        writerNameField.setBorder(BorderFactory.createEtchedBorder());
        this.add(writerNameField, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        String[] bookTypes = new String[]{"Science", "Architecture", "Novel"};
        bookTypesSelection = new JComboBox<>(bookTypes);
        bookTypesSelection.setPreferredSize(new Dimension(260, 45));
        this.add(bookTypesSelection, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(600));

        submitButton = new JButton("SUBMIT");
        submitButton.setPreferredSize(new Dimension(250, 20));
        this.add(submitButton);
    }

    public JTextField getBookNameField() {
        return bookNameField;
    }

    public JTextField getWriterNameField() {
        return writerNameField;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JComboBox<String> getBookTypesSelection() {
        return bookTypesSelection;
    }
}
