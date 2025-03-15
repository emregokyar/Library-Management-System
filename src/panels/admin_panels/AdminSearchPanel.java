package panels.admin_panels;

import database.Book;
import database.Database;
import panels.admin_search_rows.AdminBooksRow;
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

public class AdminSearchPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JLabel info;

    private final Database database;
    private List<Book> allBooks;
    private List<Book> searchedBooks;
    private List<AdminBooksRow> booksRows = new ArrayList<>();
    private PageLayout pageLayout;
    private PageScrollPane<PageLayout> scrollBookSearch;

    public AdminSearchPanel(Database database) {
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
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search Book");
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
        String infoLine = "%-16s %-17s %-15s %-15s %-15s %s".formatted("NAME",
                "WRITER",
                "TYPE",
                "AVAILABILITY",
                "UPDATE",
                "DELETE");
        info = new JLabel(infoLine);
        info.setPreferredSize(new Dimension(500, 40));
        searchPanel.add(info);

        this.add(searchPanel, BorderLayout.NORTH);


        allBooks = database.listAllBooks();
        if (allBooks!=null){
            allBooks.forEach(b -> booksRows.add(new AdminBooksRow(b)));
        }
        pageLayout = new PageLayout(booksRows);
        scrollBookSearch = new PageScrollPane<>(pageLayout);
        scrollBookSearch.setPreferredSize(new Dimension(555, 275));
        scrollBookSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollBookSearch, BorderLayout.CENTER);

        search();
        deleteBook();
        updateBook();
        revalidate();
        repaint();
    }

    public void deleteBook() {
        booksRows.forEach(b -> {
            b.getDeleteBookButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (JOptionPane.showConfirmDialog(AdminSearchPanel.this, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (database.deleteBook(b.getBookId())) {
                            allBooks = database.listAllBooks();
                            updateBookPanel();
                            revalidate();
                            repaint();
                        }
                    }
                }
            });
        });
    }

    public void updateBookPanel() {
        allBooks = database.listAllBooks();
        booksRows.clear();
        allBooks.forEach(b -> booksRows.add(new AdminBooksRow(b)));
        pageLayout = new PageLayout(booksRows);
        if (scrollBookSearch != null) {
            scrollBookSearch.setViewportView(pageLayout);
        }
        updateBook();
        deleteBook();
        revalidate();
        repaint();
    }

    public void searchBooksByName(String bookName) {
        searchedBooks = database.getBooksByName(bookName);
        booksRows.clear();

        searchedBooks.forEach(b -> booksRows.add(new AdminBooksRow(b)));
        pageLayout = new PageLayout(booksRows);
        if (scrollBookSearch != null) {
            scrollBookSearch.setViewportView(pageLayout);
        }
        updateBook();
        deleteBook();
        revalidate();
        repaint();
    }

    public void search() {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBooksByName(searchField.getText());
            }
        });
    }

    public void updateBook() {
        booksRows.forEach(b -> {
            b.getUpdateButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (JOptionPane.showConfirmDialog(AdminSearchPanel.this, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (database.bookUpdate(b.getBookId(), b.getBookName().getText(), b.getBookWriter().getText(), b.getBookType().getText())) {
                            allBooks = database.listAllBooks();
                            updateBookPanel();
                            revalidate();
                            repaint();
                        }
                    }
                }
            });
        });
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
