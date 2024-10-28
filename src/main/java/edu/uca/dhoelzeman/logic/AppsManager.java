package edu.uca.dhoelzeman.logic;

import com.opencsv.CSVReader;
import edu.uca.dhoelzeman.gui.Headers;

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



    public List<String> getColumn(String column) {
        int columnIndex = Arrays.asList(headers).indexOf(column);

        if (columnIndex == -1) {
            return Collections.emptyList();
        }

        return data.stream()
                .map(row -> row[columnIndex])
                .collect(Collectors.toList());
    }



    public String[] getFilteredHeaders() {
        return new String[] {
            Headers.Title.name(),
            Headers.DeveloperName.name(),
            Headers.Genre.name(),
            Headers.ContentRating.name(),
            Headers.ReviewsAverage.name()
        };
    }

    public List<String[]> filterImportantInformation() {
        return data.stream()
                .map(row -> new String[] {
                        row[Headers.Title.ordinal()],
                        row[Headers.DeveloperName.ordinal()],
                        row[Headers.Genre.ordinal()],
                        row[Headers.ContentRating.ordinal()],
                        row[Headers.ReviewsAverage.ordinal()]
                })
                .collect(Collectors.toList());
    }

    public String[] getCompleteRowData(String appName) {
        return data.stream()
                .filter(app -> Objects.equals(app[Headers.Title.ordinal()], appName))
                .findFirst()
                .orElse(null);
    }


    public List<String[]> getPageData(int page, int rowsPerPage) {
        int start = (page - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, data.size());
        return data.subList(start, end);
    }
}
