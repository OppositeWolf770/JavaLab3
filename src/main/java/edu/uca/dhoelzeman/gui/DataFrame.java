package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// Main class that runs the program
public class DataFrame {
    private static AppsManager appsManager;
    private static FilterPanel filterPanel;
    private static DetailsPanel detailsPanel;
    private static TablePanel tablePanel;
    private static ChartPanel chartPanel;
    private static StatsPanel statsPanel;

    // Main method to run the program
    public static void main(String[] args) {
        var dataFrame = new JFrame("Android Apps") {
            {
                setSize(new Dimension(1800, 1000));
                setExtendedState(JFrame.MAXIMIZED_HORIZ);
                setLayout(new BorderLayout());
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };

        // Loading screen while the table panel gets loaded in
        var loadingPanel = new JPanel() {{
                add(new JLabel("Loading Data. Please Wait..."));
        }};
        dataFrame.add(loadingPanel, BorderLayout.CENTER);

        // Load the table panel in the background
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                appsManager = new AppsManager("src/data.csv");

                return null;
            }

            // Create the table panel and add it to the data frame
            @Override
            protected void done() {
                detailsPanel = new DetailsPanel();
                tablePanel = new TablePanel(appsManager, detailsPanel, dataFrame);
                filterPanel = new FilterPanel(tablePanel);
                statsPanel = new StatsPanel(appsManager);

                // Create the chart panel
                var dataset = getDefaultCategoryDataset();
                chartPanel = new ChartPanel(ChartFactory.createBarChart(
                        "Most Frequent Values",
                        "Column",
                        "Count",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false
                ));


                // Remove the loading panel and add the panels
                dataFrame.remove(loadingPanel);
                dataFrame.add(filterPanel, BorderLayout.NORTH);
                dataFrame.add(detailsPanel, BorderLayout.SOUTH);
                dataFrame.add(tablePanel, BorderLayout.CENTER);
                dataFrame.add(chartPanel, BorderLayout.EAST);
                dataFrame.add(statsPanel, BorderLayout.WEST);

                dataFrame.revalidate();
                dataFrame.repaint();
            }

            // Gets the default category dataset for the chart
            private DefaultCategoryDataset getDefaultCategoryDataset() {
                var dataset = new DefaultCategoryDataset();
                Map<String, Map.Entry<String, Integer>> mostFrequentInstances = appsManager.getMostFrequentInstances();

                // Add the most frequent values to the dataset
                for (var entry : mostFrequentInstances.entrySet()) {
                    String header = entry.getKey();
                    String mostFrequentValue = entry.getValue().getKey();
                    int count = entry.getValue().getValue();
                    dataset.addValue(count, mostFrequentValue, header);
                }
                return dataset;
            }
        }.execute();
    }
}
