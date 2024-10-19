package edu.uca.dhoelzeman.logic;

import com.opencsv.CSVReader;

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
                        row[Headers.Title.index],
                        row[Headers.DeveloperName.index],
                        row[Headers.Genre.index],
                        row[Headers.ContentRating.index],
                        row[Headers.ReviewsAverage.index]
                })
                .collect(Collectors.toList());
    }

    public String[] getCompleteRowData(int rowIndex) {
        return data.get(rowIndex);
    }



    public enum Headers {
        pkgname(0),
        Ratings(1),
        Title(2),
        FourStarRatings(3),
        DeveloperAddress(4),
        LastUpdated(5),
        ReviewsAverage(6),
        Price(7),
        ThreeStarRatings(8),
        PrivacyPolicyLink(9),
        Genre(10),
        FiveStarRatings(11),
        OneStarRatings(12),
        Url(13),
        ContentRating(14),
        CurrentVersion(15),
        DeveloperEmail(16),
        AndroidVersion(17),
        DeveloperWebsite(18),
        DeveloperName(19),
        FileSize(20),
        TwoStarRatings(21),
        Downloads(22),
        ID(23),
        permission_2(24),
        litscore(25);

        public final int index;

        Headers(int index) {
            this.index = index;
        }
    }
}
