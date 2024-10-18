package edu.uca.dhoelzeman.logic;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AppsManager {
    private List<String[]> data;
    private String[] headers;
    private Map<String, List<String[]>> columnIndexMap;

    public AppsManager() {
        data = new ArrayList<>();
        columnIndexMap = new HashMap<>();
    }

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

    public List<String[]> getData() {
        return data;
    }

    public Optional<String[]> findTitle(String title) {
        return data.stream()
                .filter(row -> row.length > 0 && row[2].equalsIgnoreCase(title))
                .findFirst();
    }


    public List<String[]> filterFreeApps() {
        return columnIndexMap.get("Price").stream()
                .filter(row -> row.length > 0 && row[Headers.Price.index].equals("0.0"))
                .collect(Collectors.toList());
    }


    public List<String[]> filterByColumn(String column, String value) {
        if (!columnIndexMap.containsKey(column)) {
            throw new IllegalArgumentException("Column Not Found: " + column);
        }

        return columnIndexMap.get(column).stream()
                .filter(row -> row.length > 0 && row[Arrays.asList(headers).indexOf(column)].equalsIgnoreCase(value))
                .collect(Collectors.toList());
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
