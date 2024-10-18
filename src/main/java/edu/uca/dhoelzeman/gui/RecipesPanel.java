package edu.uca.dhoelzeman.gui;

import edu.uca.dhoelzeman.logic.AppsManager;

import javax.swing.*;

public class RecipesPanel extends JPanel {
    private final AppsManager recipesManager;

    RecipesPanel() {
        this.recipesManager = new AppsManager();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void displayProducts() {

    }
}
