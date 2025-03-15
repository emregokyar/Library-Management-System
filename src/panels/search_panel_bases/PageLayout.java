package panels.search_panel_bases;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageLayout extends JPanel {

    public PageLayout(List<? extends JPanel> rows) {
        int listSize = rows.size();
        this.setBackground(Color.lightGray);
        this.setLayout(new GridLayout(listSize, 1, 5, 5));

        for (var item : rows) {
            this.add(item);
        }

        this.setPreferredSize(new Dimension(480, listSize * 40));
    }
}