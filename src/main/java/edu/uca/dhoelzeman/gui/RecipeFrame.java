package edu.uca.dhoelzeman.gui;

import javax.swing.*;
import java.awt.*;

public class RecipeFrame {
    public static final int SCROLL_SPEED = 16;

    public static void main(String[] args) {
        var recipesFrame = new JFrame("Recipes") {
            {
                setSize(new Dimension(1000, 800));
                setLocationRelativeTo(null);
                setVisible(true);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };

        var recipesPanel = new RecipesPanel();
        recipesPanel.displayProducts();

        var scrollPane = new JScrollPane(recipesPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        recipesFrame.add(scrollPane);

        recipesFrame.revalidate();
        recipesFrame.repaint();
    }
}
