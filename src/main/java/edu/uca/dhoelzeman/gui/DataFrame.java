package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DataFrame {
    private static AppsManager appsManager;
    private static FilterPanel filterPanel;
    private static DetailsPanel detailsPanel;
    private static TablePanel tablePanel;

    public static void main(String[] args) {
        var dataFrame = new JFrame("Android Apps") {
            {
//                setSize(new Dimension(1000, 800));
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setLayout(new BorderLayout());
                setLocationRelativeTo(null);
                setVisible(true);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };

//        var chart = new JFreeChart("Applications Overview", new Plot() {
//            @Override
//            public String getPlotType() {
//                return "";
//            }
//
//            @Override
//            public void draw(Graphics2D graphics2D, Rectangle2D rectangle2D, Point2D point2D, PlotState plotState, PlotRenderingInfo plotRenderingInfo) {
//
//            }
//        });

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

                dataFrame.remove(loadingPanel);
                dataFrame.add(filterPanel, BorderLayout.NORTH);
                dataFrame.add(detailsPanel, BorderLayout.SOUTH);
                dataFrame.add(tablePanel, BorderLayout.CENTER);

                dataFrame.revalidate();
                dataFrame.repaint();
            }
        }.execute();
    }
}
