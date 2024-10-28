package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Panel that displays the table of apps
public class TablePanel extends JPanel {
    private final JTable table;
    private final JFrame parentFrame;
    private final JDialog sortingDialog;
    private final Map<Integer, SortOrder> columnSortOrderMap;
    private final TableRowSorter<TableModel> sorter;

    // Constructor
    public TablePanel(AppsManager appsManager, DetailsPanel detailsPanel, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.table = new JTable(appsManager.createTableModel());
        this.sortingDialog = createSortingDialog(parentFrame);
        this.columnSortOrderMap = new HashMap<>();
        this.sorter = new TableRowSorter<>(table.getModel());
        this.table.setRowSorter(sorter);

        var tableHeader = table.getTableHeader();

        // Sorts the table when the user clicks on a column header
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                showSortingDialog();
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        sortTableByColumn(column);
                        return null;
                    }

                    @Override
                    protected void done() {
                        hideSortingDialog();
                    }
                }.execute();
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Show app information for highlighted app in the details panel when user clicks on a row
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String appName = (String) table.getValueAt(selectedRow, 0);
                    String[] completeRowData = appsManager.getCompleteRowData(appName);
                    try {
                        detailsPanel.displayAppInfo(completeRowData);
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    // Gets the current table information
    public JTable getTable() {
        return table;
    }

    // Applies filters based on information in the provided filterFields
    public void applyFilters(JTextField[] filterFields) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        for (int i = 0; i < filterFields.length; i++) {
            String text = filterFields[i].getText();
            if (!text.trim().isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + text, i));
            }
        }
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
    }


    // Sorts the column in the table depending on its current sorting method
    private void sortTableByColumn(int column) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

        SortOrder currentOrder = columnSortOrderMap.getOrDefault(column, SortOrder.UNSORTED);
        SortOrder newOrder;

        // Change the sortingOrder based on the currently selected sorting order
        switch (currentOrder) {
            case UNSORTED:
                newOrder = SortOrder.ASCENDING;
                break;
            case ASCENDING:
                newOrder = SortOrder.DESCENDING;
                break;
            default:
                newOrder = SortOrder.UNSORTED;
        }

        // Set the new table order if it is not unsorted
        if (newOrder != SortOrder.UNSORTED) {
            sortKeys.add(new RowSorter.SortKey(column, newOrder));
            sorter.setSortKeys(sortKeys);
            table.setRowSorter(sorter);
        } else {
            table.setRowSorter(null);
        }

        columnSortOrderMap.put(column, newOrder);
    }

    // Creates the sorting Dialog Modal
    private JDialog createSortingDialog(JFrame parentFrame) {
        JDialog sortingDialog = new JDialog(parentFrame, "Sorting", Dialog.ModalityType.APPLICATION_MODAL);
        sortingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        sortingDialog.setSize(200, 100);
        sortingDialog.setLocationRelativeTo(parentFrame);
        sortingDialog.add(new JLabel("Sorting. Please Wait...", SwingConstants.CENTER));
        return sortingDialog;
    }

    // Displays the Sorting Dialog Modal when the user clicks on a column header
    private void showSortingDialog() {
        SwingUtilities.invokeLater(() -> {
            parentFrame.setEnabled(false);
            sortingDialog.setVisible(true);
        });
    }

    // Hides the Sorting Dialog Modal when the sorting is complete
    private void hideSortingDialog() {
        SwingUtilities.invokeLater(() -> {
            sortingDialog.setVisible(false);
            parentFrame.setEnabled(true);
        });
    }
}

