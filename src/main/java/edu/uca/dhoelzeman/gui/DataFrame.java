package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;
import java.awt.*;

public class DataFrame {
    public static final AppsManager appsManager = new AppsManager("src\\data.csv");
    public static final int SCROLL_SPEED = 16;

    public static void main(String[] args) {
        var dataFrame = new JFrame("Recipes") {
            {
                setSize(new Dimension(1000, 800));
//                setExtendedState(JFrame.MAXIMIZED_BOTH);
//                setUndecorated(true);
                setLayout(new BorderLayout());
//                setLocationRelativeTo(null);
                setVisible(true);
//                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };


        // FilterPanel to hold controls to manipulate the display of the data

        var filterPanel = new FilterPanel();
        dataFrame.add(filterPanel, BorderLayout.NORTH);

        var detailsPanel = new DetailsPanel();
        dataFrame.add(detailsPanel, BorderLayout.SOUTH);

        var tablePanel = new TablePanel(appsManager, detailsPanel);
        dataFrame.add(tablePanel, BorderLayout.CENTER);


        dataFrame.revalidate();
        dataFrame.repaint();
    }
}
