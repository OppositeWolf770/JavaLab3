package edu.uca.dhoelzeman.gui;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private final JPanel generalPanel = new JPanel();
    private final JPanel developerPanel = new JPanel();
    private final JPanel technicalPanel = new JPanel();

    DetailsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
        developerPanel.setLayout(new BoxLayout(developerPanel, BoxLayout.Y_AXIS));
        technicalPanel.setLayout(new BoxLayout(technicalPanel, BoxLayout.Y_AXIS));

        add(generalPanel);
        add(developerPanel);
        add(technicalPanel);
    }


    @Override
    public void doLayout() {
        super.doLayout();

        int width = getWidth() / 3;
        generalPanel.setPreferredSize(new Dimension(width, 150));
        developerPanel.setPreferredSize(new Dimension(width, 150));
        technicalPanel.setPreferredSize(new Dimension(width, 150));
    }


    public void displayAppInfo(String[] rowData) {
        generalPanel.removeAll();
        developerPanel.removeAll();
        technicalPanel.removeAll();

        generalPanel.add(new JLabel("App Info") {
            {
                setFont(getFont().deriveFont(Font.BOLD));
            }
        });

        developerPanel.add(new JLabel("Developer Info") {
            {
                setFont(getFont().deriveFont(Font.BOLD));
            }
        });

        technicalPanel.add(new JLabel("Technical Info") {
            {
                setFont(getFont().deriveFont(Font.BOLD));
            }
        });

//        StringBuilder info = new StringBuilder("<html>");
//
//        for (String detail : appInfo) {
//            info.append(detail).append("<br>");
//        }
//        info.append("</html>");

        revalidate();
        repaint();
    }
}
