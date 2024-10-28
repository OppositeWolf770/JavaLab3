package edu.uca.dhoelzeman.gui;

import javax.swing.*;

// Panel that contains the filter fields for the tablez
public class FilterPanel extends JPanel {
    private final JTextField[] filterFields;

    FilterPanel(TablePanel tablePanel) {
        JTable table = tablePanel.getTable();
        JButton applyFiltersButton = new JButton("Apply Filters");
        JButton clearFiltersButton = new JButton("Clear Filters");
        filterFields = new JTextField[table.getColumnCount()];

        // Sets up each filter field and adds it to the panel
        for (int i = 0; i < filterFields.length; i++) {
            JPanel filterPanel = new JPanel();
            filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

            filterFields[i] = new JTextField(10);
            filterFields[i].addActionListener(_ -> tablePanel.applyFilters(filterFields));

            filterPanel.add(new JLabel(table.getColumnName(i) + " Filter"));
            filterPanel.add(filterFields[i]);
            add(filterPanel);
        }

        add(applyFiltersButton);
        add(clearFiltersButton);

        // Apply the filters when the Apply Filters button is clicked
        applyFiltersButton.addActionListener(_ -> tablePanel.applyFilters(filterFields));

        // Clear the filters when the Clear Filters button is clicked
        clearFiltersButton.addActionListener(_ -> {
            for (int i = 0; i < filterFields.length; i++) {
                filterFields[i].setText("");
            }

            tablePanel.applyFilters(filterFields);
        });
    }
}
