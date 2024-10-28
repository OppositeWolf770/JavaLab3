package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;

// Panel that displays statistics about the apps
public class StatsPanel extends JPanel {
    public StatsPanel(AppsManager appsManager) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        add(new JLabel("<html><u>Statistics</u></html>", SwingConstants.CENTER));
        add(new JPanel() {
            {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                // Add statistics
                add(new JLabel("Total Apps: " + appsManager.getTotalApps()));
                add(new JLabel("Total Free Apps: " + appsManager.getTotalFreeApps()));
                add(new JLabel("Total Paid Apps: " + appsManager.getTotalPaidApps()));
                add(new JLabel("Average Price of Paid Apps: " + String.format("%.2f", appsManager.getAveragePriceOfPaidApps())));
                add(new JLabel("Average Rating of Free Apps: " + String.format("%.2f", appsManager.getAverageRatingOfFreeApps())));
                add(new JLabel("Average Rating of Paid Apps: " + String.format("%.2f", appsManager.getAverageRatingOfPaidApps())));
            }
        });
    }
}
