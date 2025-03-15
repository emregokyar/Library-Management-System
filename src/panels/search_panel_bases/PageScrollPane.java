package panels.search_panel_bases;

import javax.swing.*;

public class PageScrollPane<T extends JPanel> extends JScrollPane {
    public PageScrollPane(T layout) {
        this.setViewportView(layout);
    }
}
