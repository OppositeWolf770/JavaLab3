package edu.uca.dhoelzeman.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoldTitleCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (table.getColumnName(column).equals("Title")) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        }

        return c;
    }
}
