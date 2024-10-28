package edu.uca.dhoelzeman.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilterPanel extends JPanel {
    private final JTextField[] filterFields;
    private final JTable table;

    FilterPanel(TablePanel tablePanel) {
        table = tablePanel.getTable();
        filterFields = new JTextField[table.getColumnCount()];

        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i] = new JTextField(10);


            add(filterFields[i]);
        }

        // Add the Document listeners to the filterFields
        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    tablePanel.applyFilters(filterFields);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    tablePanel.applyFilters(filterFields);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    tablePanel.applyFilters(filterFields);
                }
            });
        }
    }
}
