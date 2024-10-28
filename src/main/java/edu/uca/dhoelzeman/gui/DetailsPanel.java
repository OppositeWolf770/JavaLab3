package edu.uca.dhoelzeman.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DetailsPanel extends JPanel {
    private final JPanel generalPanel = new JPanel();
    private final JPanel ratingsPanel = new JPanel();
    private final JPanel developerPanel = new JPanel();
    private final JPanel technicalPanel = new JPanel();

    DetailsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        var border = BorderFactory.createLineBorder(Color.black);

        // Set the layout for the panels
        generalPanel.setLayout(new BorderLayout());
        ratingsPanel.setLayout(new BorderLayout());
        developerPanel.setLayout(new BorderLayout());
        technicalPanel.setLayout(new BorderLayout());

        // Add border to the panels
        generalPanel.setBorder(border);
        ratingsPanel.setBorder(border);
        developerPanel.setBorder(border);
        technicalPanel.setBorder(border);

        // Add the panels to the DetailsPanel
        add(generalPanel);
        add(ratingsPanel);
        add(developerPanel);
        add(technicalPanel);
    }


    // Displays all the app information for the specified row
    public void displayAppInfo(String[] rowData) throws URISyntaxException {
        generalPanel.removeAll();
        ratingsPanel.removeAll();
        developerPanel.removeAll();
        technicalPanel.removeAll();



        // Panel to hold general app information
        generalPanel.add(new JLabel("<html><u>App Info</u></html>", SwingConstants.CENTER), BorderLayout.NORTH);
        generalPanel.add(new JPanel() {
            {
                String price = rowData[Headers.Price.ordinal()]; // The price of the app

                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel(Headers.Title + ": " + rowData[Headers.Title.ordinal()]));
                add(new JLabel((Headers.Price + ": " + (price.equals("0.0") ? "Free" : price))));
                add(new JLabel(Headers.Genre + ": " + rowData[Headers.Genre.ordinal()]));
                add(new JLabel(Headers.ContentRating + ": " + rowData[Headers.ContentRating.ordinal()]));
                add(new LinkLabel(Headers.Url, rowData[Headers.Url.ordinal()]));
            }
        }, BorderLayout.CENTER);

        // Panel to hold the ratings information
        ratingsPanel.add(new JLabel("<html><u>Ratings</u></html>", SwingConstants.CENTER), BorderLayout.NORTH);
        ratingsPanel.add(new JPanel() {
            {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel("★★★★★: " + rowData[Headers.FiveStarRatings.ordinal()]), SwingConstants.CENTER);
                add(new JLabel("★★★★☆: " + rowData[Headers.FourStarRatings.ordinal()]));
                add(new JLabel("★★★☆☆: " + rowData[Headers.ThreeStarRatings.ordinal()]));
                add(new JLabel("★★☆☆☆: " + rowData[Headers.TwoStarRatings.ordinal()]));
                add(new JLabel("★☆☆☆☆: " + rowData[Headers.OneStarRatings.ordinal()]));
                add(new JLabel(Headers.ReviewsAverage + ": " + rowData[Headers.ReviewsAverage.ordinal()]));
            }
        }, BorderLayout.CENTER);

        // Panel to hold developer information
        developerPanel.add(new JLabel("<html><u>Developer Info</u></html>", SwingConstants.CENTER), BorderLayout.NORTH);
        developerPanel.add(new JPanel() {
            {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel(Headers.DeveloperName + ": " + rowData[Headers.DeveloperName.ordinal()]));
                add(new JLabel(Headers.DeveloperAddress + ": " + rowData[Headers.DeveloperAddress.ordinal()]));
                add(new JLabel(Headers.DeveloperEmail + ": " + rowData[Headers.DeveloperEmail.ordinal()]));
                add(new LinkLabel(Headers.DeveloperWebsite, rowData[Headers.DeveloperWebsite.ordinal()]));
            }
        });

        // Panel to hold the technical app details
        technicalPanel.add(new JLabel("<html><u>Technical Info</u></html>", SwingConstants.CENTER), BorderLayout.NORTH);
        technicalPanel.add(new JPanel() {
            {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                add(new JLabel(Headers.pkgname + ": " + rowData[Headers.Title.ordinal()]), BorderLayout.CENTER);
                add(new JLabel(Headers.CurrentVersion + ": " + rowData[Headers.CurrentVersion.ordinal()]));
                add(new JLabel(Headers.AndroidVersion + ": " + rowData[Headers.AndroidVersion.ordinal()]));
                add(new JLabel(Headers.LastUpdated + ": " + rowData[Headers.LastUpdated.ordinal()]));
                add(new JLabel(Headers.Downloads + ": " + rowData[Headers.Downloads.ordinal()]));
                add(new JLabel(Headers.FileSize + ": " + rowData[Headers.FileSize.ordinal()]));
                add(new LinkLabel(Headers.PrivacyPolicyLink, rowData[Headers.PrivacyPolicyLink.ordinal()]));
                add(new JLabel(Headers.permission_2 + ": " + rowData[Headers.permission_2.ordinal()]));
                add(new JLabel(Headers.litscore + ": " + rowData[Headers.litscore.ordinal()]));
            }
        });

        revalidate();
        repaint();
    }


    // A label that holds a hyperlink to a website
    static class LinkLabel extends JLabel {
        LinkLabel(Headers header, String uri) throws URISyntaxException {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setText("<HTML><FONT color=\"#000099\"><U>" + header + "</U></FONT></HTML>");
            setToolTipText(uri);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        if (uri.isEmpty()) {
                            Desktop.getDesktop().browse(new URI("https://nuttyapps.com/privacypolicy/"));
                        } else {
                            Desktop.getDesktop().browse(new URI(uri));
                        }
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
}
