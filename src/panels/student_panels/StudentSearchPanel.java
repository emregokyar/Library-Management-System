package panels.student_panels;

import database.Book;
import database.Database;
import database.Student;
import panels.search_panel_bases.PageLayout;
import panels.search_panel_bases.PageScrollPane;
import panels.student_search_rows.StudentBookSearchRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class StudentSearchPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JLabel info;
    private int totalBorrowedBooks = 0;

    private final Database database;
    private Student student;
    private List<Book> allBooks;
    private List<Book> searchedBooks;
    private List<StudentBookSearchRow> booksRows = new ArrayList<>();
    private PageLayout pageLayout;
    private PageScrollPane<PageLayout> scrollBookSearch;
    private final int maxOwnedBook = 5;

    public StudentSearchPanel(Database database, Student student) {
        if (student != null) {
            this.student = student;
            totalBorrowedBooks = database.getOwnedBooks(student).size() - 1;
        }
        this.database = database;
        this.setPreferredSize(new Dimension(600, 400));
        this.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setPreferredSize(new Dimension(555, 80));

        searchField = new JTextField("Search Book");
        searchField.setPreferredSize(new Dimension(500, 40));
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search Book")){
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()){
                    searchField.setText("Search Book");
                    searchField.setForeground(Color.GRAY);
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
        String infoLine = "%-20s %-20s %-23s %-27s %s".formatted("NAME",
                "WRITER",
                "TYPE",
                "AVAILABILITY",
                "GET");
        info = new JLabel(infoLine);
        info.setPreferredSize(new Dimension(500, 40));
        searchPanel.add(info);
        this.add(searchPanel, BorderLayout.NORTH);


        allBooks = database.listAllBooks();
        if (allBooks != null) {
            allBooks.forEach(b -> booksRows.add(new StudentBookSearchRow(b)));
        }
        pageLayout = new PageLayout(booksRows);
        scrollBookSearch = new PageScrollPane<>(pageLayout);
        scrollBookSearch.setPreferredSize(new Dimension(555, 275));
        scrollBookSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollBookSearch, BorderLayout.CENTER);

        search();
        getBook();
        revalidate();
        repaint();
    }

    public void getBook() {
        booksRows.forEach(b -> {
            if (b.getAvailability().getText()=="AVAILABLE") {
                b.getGetButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (JOptionPane.showConfirmDialog(StudentSearchPanel.this, "Borrowing this book. Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
                        ) {
                            totalBorrowedBooks = database.getOwnedBooks(student).size() - 1;
                            if (totalBorrowedBooks < maxOwnedBook) {
                                database.addOwnedBook(student, b.getBookId());
                            } else {
                                JOptionPane.showConfirmDialog(StudentSearchPanel.this, "You might have been exceeding max number of books or book is not available", "WARNING", JOptionPane.DEFAULT_OPTION);
                            }
                        }
                        updateBookPanel();
                        revalidate();
                        repaint();
                    }
                });
            }
        });
    }

    public void updateBookPanel() {
        allBooks = database.listAllBooks();
        booksRows.clear();
        allBooks.forEach(b -> booksRows.add(new StudentBookSearchRow(b)));
        pageLayout = new PageLayout(booksRows);
        if (scrollBookSearch != null) {
            scrollBookSearch.setViewportView(pageLayout);
        }
        getBook();
        revalidate();
        repaint();
    }

    public void searchBookByName(String bookName) {
        searchedBooks = database.getBooksByName(bookName);
        booksRows.clear();

        searchedBooks.forEach(b -> booksRows.add(new StudentBookSearchRow(b)));
        pageLayout = new PageLayout(booksRows);
        if (scrollBookSearch != null) {
            scrollBookSearch.setViewportView(pageLayout);
        }
        getBook();
        revalidate();
        repaint();
    }

    public void search() {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBookByName(searchField.getText());
            }
        });
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
