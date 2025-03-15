package panels.student_panels;

import database.Book;
import database.Database;
import database.Student;
import panels.search_panel_bases.PageLayout;
import panels.search_panel_bases.PageScrollPane;
import panels.student_search_rows.StudentMyBooksRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class StudentBooksPanel extends JPanel {
    private JLabel info;
    private final Database database;
    private Student student;

    private List<Book> ownedBooks;
    private List<StudentMyBooksRow> myBooksRows = new ArrayList<>();
    private PageLayout pageLayout;
    private PageScrollPane<PageLayout> scrollMyBooksPane;

    public StudentBooksPanel(Database database, Student student) {
        this.database = database;
        this.student = student;
        this.setPreferredSize(new Dimension(600, 400));
        this.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        infoPanel.setPreferredSize(new Dimension(555, 40));

        String infoLine = "%-25s %-28s %-32s %s".formatted("NAME",
                "WRITER",
                "TYPE",
                "RETURN");
        info = new JLabel(infoLine);
        info.setPreferredSize(new Dimension(500, 40));
        infoPanel.add(info);
        this.add(infoPanel, BorderLayout.NORTH);


        ownedBooks = database.getOwnedBooks(student);
        ownedBooks.forEach(b -> myBooksRows.add(new StudentMyBooksRow(b)));
        pageLayout = new PageLayout(myBooksRows);
        scrollMyBooksPane = new PageScrollPane<>(pageLayout);
        scrollMyBooksPane.setPreferredSize(new Dimension(555, 275));
        scrollMyBooksPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollMyBooksPane, BorderLayout.CENTER);

        updateMyBooksPanel();
        returnBook();
        revalidate();
        repaint();
    }

    public void updateMyBooksPanel() {
        ownedBooks = database.getOwnedBooks(student);
        myBooksRows.clear();
        ownedBooks.forEach(b -> myBooksRows.add(new StudentMyBooksRow(b)));
        pageLayout = new PageLayout(myBooksRows);
        if (scrollMyBooksPane != null) {
            scrollMyBooksPane.setViewportView(pageLayout);
        }
        returnBook();
        revalidate();
        repaint();
    }

    public void returnBook() {
        myBooksRows.forEach(b -> {
            b.getReturnButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (JOptionPane.showConfirmDialog(StudentBooksPanel.this, "Returning the book. Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        student.getOwnedBooks().remove(b.getBook());
                        database.returnOwnedBook(student, b.getBookId());
                    }
                    updateMyBooksPanel();
                    revalidate();
                    repaint();
                }
            });
        });
    }
}
