package edu.uca.dhoelzeman.logic;

import com.opencsv.CSVReader;
import edu.uca.dhoelzeman.gui.Headers;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AppsManager {
    private final List<String[]> data;
    private String[] headers;
    private final Map<String, List<String[]>> columnIndexMap;

    public AppsManager(String csvFile) {
        data = new ArrayList<>();
        columnIndexMap = new HashMap<>();

        loadCSV(csvFile);
    }


    // Loads the csv file at the specified path
    public void loadCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            headers = reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
                for (int i = 0; i < line.length; i++) {
                    columnIndexMap.computeIfAbsent(headers[i], k -> new ArrayList<>()).add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Gets the specified column from the data
    public List<String> getColumn(Headers header) {
        return data.stream()
                .map(row -> row[header.ordinal()])
                .collect(Collectors.toList());
    }



    // Gets the headers of the data to be displayed in the table
    public String[] getFilteredHeaders() {
        return new String[] {
            Headers.Title.name(),
            Headers.DeveloperName.name(),
            Headers.Genre.name(),
            Headers.ContentRating.name(),
            Headers.ReviewsAverage.name(),
            Headers.Downloads.name()
        };
    }

    // Filters the data to only include the important information
    public List<String[]> filterImportantInformation() {
        return data.stream()
                .map(row -> new String[] {
                        row[Headers.Title.ordinal()],
                        row[Headers.DeveloperName.ordinal()],
                        row[Headers.Genre.ordinal()],
                        row[Headers.ContentRating.ordinal()],
                        row[Headers.ReviewsAverage.ordinal()],
                        row[Headers.Downloads.ordinal()]
                })
                .collect(Collectors.toList());
    }

    // Creates a table model with the filtered data
    public DefaultTableModel createTableModel() {
        List<String[]> filteredData = filterImportantInformation();
        String[] filteredHeaders = getFilteredHeaders();

        return new DefaultTableModel(filteredData.toArray(new Object[][]{}), filteredHeaders);
    }

    // Gets the data of the row with the specified app name
    public String[] getCompleteRowData(String appName) {
        return data.stream()
                .filter(app -> Objects.equals(app[Headers.Title.ordinal()], appName))
                .findFirst()
                .orElse(null);
    }


    // Gets the most frequent instances of each column
    public Map<String, Map.Entry<String, Integer>> getMostFrequentInstances() {
        Map<String, Map.Entry<String, Integer>> mostFrequentInstances = new HashMap<>();
        String[] filteredHeaders = getFilteredHeaders();

        // Find the most frequent instance of each column
        for (String header : filteredHeaders) {
            List<String> columnData = getColumn(Headers.valueOf(header));
            Map<String, Integer> frequencyMap = new HashMap<>();

            // Count the frequency of each value in the column
            for (String value : columnData) {
                frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            }

            Map.Entry<String, Integer> mostFrequentEntry = null;

            // Find the most frequent entry in the column
            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                if (mostFrequentEntry == null || entry.getValue() > mostFrequentEntry.getValue()) {
                    mostFrequentEntry = entry;
                }
            }

            mostFrequentInstances.put(header, mostFrequentEntry);
        }

        return mostFrequentInstances;
    }


    // Gets the total number of apps
    public int getTotalApps() {
        return data.size();
    }

    // Gets the total number of free apps
    public int getTotalFreeApps() {
        return (int) data.stream()
                .filter(app -> app[Headers.Price.ordinal()].equals("0.0"))
                .count();
    }

    // Gets the total number of paid apps
    public int getTotalPaidApps() {
        return (int) data.stream()
                .filter(app -> !app[Headers.Price.ordinal()].equals("0.0"))
                .count();
    }

    // Gets the average price of paid apps
    public double getAveragePriceOfPaidApps() {
        return data.stream()
                .filter(app -> !app[Headers.Price.ordinal()].equals("0.0"))
                .mapToDouble(app -> Double.parseDouble(app[Headers.Price.ordinal()]))
                .average()
                .orElse(0.0);
    }

    // Gets the average rating of free apps
    public double getAverageRatingOfFreeApps() {
        return data.stream()
                .filter(app -> app[Headers.Price.ordinal()].equals("0.0"))
                .mapToDouble(app -> Double.parseDouble(app[Headers.ReviewsAverage.ordinal()]))
                .average()
                .orElse(0.0);
    }

    // Gets the average rating of paid apps
    public double getAverageRatingOfPaidApps() {
        return data.stream()
                .filter(app -> !app[Headers.Price.ordinal()].equals("0.0"))
                .mapToDouble(app -> Double.parseDouble(app[Headers.ReviewsAverage.ordinal()]))
                .average()
                .orElse(0.0);
    }
}
