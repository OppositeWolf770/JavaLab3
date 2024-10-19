package edu.uca.dhoelzeman.gui;

import javax.swing.*;

public class FilterPanel extends JPanel {
    JTextField searchBar = new JTextField(10);
    JCheckBox sortName = new JCheckBox("Sort by Name (Descending)");

    FilterPanel() {
        add(searchBar);
        add(sortName);
    }
}
