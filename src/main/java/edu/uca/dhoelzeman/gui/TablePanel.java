package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.util.regex.PatternSyntaxException;

public class TablePanel extends JPanel {
    private final JTable table;
    private final JFrame parentFrame;
    private final JDialog sortingDialog;
    private final Map<Integer, SortOrder> columnSortOrderMap;
    private final TableRowSorter<TableModel> sorter;
//    private final JTextField[] filterFields;

    public TablePanel(AppsManager appsManager, DetailsPanel detailsPanel, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.table = new JTable(new DefaultTableModel(appsManager.filterImportantInformation().toArray(new Object[][]{}), appsManager.getFilteredHeaders()));
        this.sortingDialog = createSortingDialog(parentFrame);
        this.columnSortOrderMap = new HashMap<>();
        this.sorter = new TableRowSorter<>(table.getModel());
        this.table.setRowSorter(sorter);
//        this.filterFields = new JTextField[table.getColumnCount()];

        var tableHeader = table.getTableHeader();
//        tableHeader.setLayout(new GridLayout(1, table.getColumnCount()));

//        for (int i = 0; i < table.getColumnCount(); i++) {
//            JPanel headerPanel = new JPanel(new BorderLayout());
//            JLabel headerLabel = new JLabel(table.getColumnName(i));
//            JTextField filterField = new JTextField();
//            filterFields[i] = filterField;
//
//            headerPanel.add(headerLabel, BorderLayout.NORTH);
//            headerPanel.add(filterField, BorderLayout.SOUTH);
//            tableHeader.add(headerPanel);

//            filterField.getDocument().addDocumentListener(new DocumentListener() {
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    applyFilters();
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    applyFilters();
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    applyFilters();
//                }
//            });
//        }
//
//        revalidate();
//        repaint();

        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                showSortingDialog();
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
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

        // Show app information for highlighted app in the details panel
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

    public JTable getTable() {
        return table;
    }

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


    private void sortTableByColumn(int column) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

        SortOrder currentOrder = columnSortOrderMap.getOrDefault(column, SortOrder.UNSORTED);
        SortOrder newOrder;

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

        if (newOrder != SortOrder.UNSORTED) {
            sortKeys.add(new RowSorter.SortKey(column, newOrder));
            sorter.setSortKeys(sortKeys);
            table.setRowSorter(sorter);
        } else {
            table.setRowSorter(null);
        }

        columnSortOrderMap.put(column, newOrder);
    }

    private JDialog createSortingDialog(JFrame parentFrame) {
        JDialog sortingDialog = new JDialog(parentFrame, "Sorting", Dialog.ModalityType.APPLICATION_MODAL);
        sortingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        sortingDialog.setSize(200, 100);
        sortingDialog.setLocationRelativeTo(parentFrame);
        sortingDialog.add(new JLabel("Sorting. Please Wait...", SwingConstants.CENTER));
        return sortingDialog;
    }

    private void showSortingDialog() {
        SwingUtilities.invokeLater(() -> {
            parentFrame.setEnabled(false);
            sortingDialog.setVisible(true);
        });
    }

    private void hideSortingDialog() {
        SwingUtilities.invokeLater(() -> {
            sortingDialog.setVisible(false);
            parentFrame.setEnabled(true);
        });
    }
}

