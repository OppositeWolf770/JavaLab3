package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TablePanel extends JPanel {
    public TablePanel(AppsManager appsManager, DetailsPanel detailsPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] headers = appsManager.getFilteredHeaders();
        List<String[]> data = appsManager.filterImportantInformation();

        // Convert List<String[]> to a 2D array
        String[][] dataArray = data.toArray(new String[0][1]);

        DefaultTableModel tableModel = new DefaultTableModel(dataArray, headers);

        JTable table = new JTable(tableModel);

        table.getColumnModel().getColumn(0).setCellRenderer(new BoldTitleCellRenderer());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String[] completeRowData = appsManager.getCompleteRowData(selectedRow);
                    detailsPanel.displayAppInfo(completeRowData);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}

