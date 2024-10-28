package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;
import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class DataFrame {
    private static AppsManager appsManager;
    private static FilterPanel filterPanel;
    private static DetailsPanel detailsPanel;
    private static TablePanel tablePanel;
    private static ChartPanel chartPanel;

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
                appsManager = new AppsManager("src\\data.csv");

                return null;
            }

            @Override
            protected void done() {
                detailsPanel = new DetailsPanel();
                tablePanel = new TablePanel(appsManager, detailsPanel, dataFrame);
                filterPanel = new FilterPanel(tablePanel);

                var dataset = new DefaultCategoryDataset();
                var reviewsAverage = appsManager.getColumn(Headers.ReviewsAverage);

                var doubleReviewsAverage = reviewsAverage.stream()
                        .map(Double::parseDouble)
                        .toList();


                Mean mean = new Mean();
                double meanValue = mean.evaluate(doubleReviewsAverage.stream().mapToDouble(Double::doubleValue).toArray());

                dataset.addValue(meanValue, "Average Ratings", "Average Ratings");

                chartPanel = new ChartPanel(ChartFactory.createBarChart(
                        "Sample Chart",
                        "Category",
                        "Value",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, false
                ));

                dataFrame.remove(loadingPanel);
                dataFrame.add(filterPanel, BorderLayout.NORTH);
                dataFrame.add(detailsPanel, BorderLayout.SOUTH);
                dataFrame.add(tablePanel, BorderLayout.CENTER);
                dataFrame.add(chartPanel, BorderLayout.EAST);

                dataFrame.revalidate();
                dataFrame.repaint();
            }
        }.execute();
    }
}
