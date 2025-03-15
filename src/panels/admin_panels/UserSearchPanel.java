package panels.admin_panels;

import database.Database;
import database.Student;
import panels.admin_search_rows.AdminStudentRow;
import panels.search_panel_bases.PageLayout;
import panels.search_panel_bases.PageScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UserSearchPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JLabel info;

    private List<Student> searchedStudents;
    private java.util.List<Student> allStudents;
    private List<AdminStudentRow> studentRows = new ArrayList<>();
    private PageLayout pageLayout;
    private PageScrollPane<PageLayout> scrollStudentSearch;

    private final Database database;


    public UserSearchPanel(Database database) {
        this.database = database;

        this.setPreferredSize(new Dimension(600, 400));
        this.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setPreferredSize(new Dimension(555, 80));

        searchField = new JTextField("Search User");
        searchField.setPreferredSize(new Dimension(500, 40));
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search User")){
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()){
                    searchField.setText("Search User");
                    searchField.setForeground(Color.BLACK);
                }
            }
        });
        searchField.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.add(searchField);

        searchButton = new JButton("\uD83D\uDD0D");
        searchButton.setPreferredSize(new Dimension(40, 40));
        searchButton.setBorder(BorderFactory.createEtchedBorder());
        searchPanel.add(searchButton);

        this.add(Box.createHorizontalStrut(600));

        String infoLine = "%-18s %-19s %-20s %-22s %s".formatted("NAME", "LASTNAME", "EMAIL", "BOOKS", "DELETE");
        info = new JLabel(infoLine);
        info.setPreferredSize(new Dimension(500, 40));
        searchPanel.add(info);

        this.add(searchPanel, BorderLayout.NORTH);

        allStudents = database.listAllStudents();
        allStudents.forEach(s -> studentRows.add(new AdminStudentRow(s)));

        pageLayout = new PageLayout(studentRows);
        scrollStudentSearch = new PageScrollPane<>(pageLayout);
        scrollStudentSearch.setPreferredSize(new Dimension(555, 275));
        scrollStudentSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollStudentSearch, BorderLayout.CENTER);

        search();
        deleteUser();
        revalidate();
        repaint();
    }

    private void deleteUser() {
        studentRows.forEach(s -> {
            s.getDeleteStudentButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (JOptionPane.showConfirmDialog(UserSearchPanel.this, "Are you sure", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (database.deleteStudent(s.getStudentId())) {
                            updateSearchPanel();
                            revalidate();
                            repaint();
                        }
                    }
                }
            });
        });
    }

    public void updateSearchPanel() {

        allStudents = database.listAllStudents();
        studentRows.clear();
        allStudents.forEach(s -> studentRows.add(new AdminStudentRow(s)));
        pageLayout = new PageLayout(studentRows);
        if (scrollStudentSearch != null) {
            scrollStudentSearch.setViewportView(pageLayout);
        }
        deleteUser();
        revalidate();
        repaint();
    }

    public void searchStudents(String name) {
        searchedStudents = database.findStudentByName(name);
        studentRows.clear();

        searchedStudents.forEach(s -> studentRows.add(new AdminStudentRow(s)));
        pageLayout = new PageLayout(studentRows);
        if (scrollStudentSearch != null) {
            scrollStudentSearch.setViewportView(pageLayout);
        }
        deleteUser();
        revalidate();
        repaint();
    }

    public void search(){
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchStudents(searchField.getText());
            }
        });
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
