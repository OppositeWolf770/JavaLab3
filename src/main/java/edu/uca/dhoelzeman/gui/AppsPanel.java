package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;
import edu.uca.dhoelzeman.logic.AppsManager.Headers;

import javax.swing.*;
import java.awt.*;

public class AppsPanel extends JPanel {
    private final AppsManager appsManager = new AppsManager("src\\data.csv");
    public static final int MAX_DISPLAY_ITEMS = 100;

    AppsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void displayProducts() {
        var titles = appsManager.getColumn("Title");

        titles.stream()
                .limit(MAX_DISPLAY_ITEMS)
                .forEach(title -> {
                    var titleLabel = new JButton(title) {
                        {
                            setFocusPainted(false);
                            setMargin(new Insets(0, 0, 0, 0));
                            setContentAreaFilled(false);
                            setBorderPainted(false);
                            setOpaque(false);
                        }
                    };

                    titleLabel.addActionListener(e -> {

                    });

                    this.add(titleLabel);

                });
//
//        apps.stream()
//                .limit(MAX_DISPLAY_ITEMS)
//                .forEach(line -> this.add(new JLabel(line[Headers.Title.index])));
//
//        revalidate();
//        repaint();
    }
}
