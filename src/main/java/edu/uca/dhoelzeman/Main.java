package edu.uca.dhoelzeman;

import edu.uca.dhoelzeman.logic.AppsManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var appsManager = new AppsManager();
        appsManager.loadCSV("src\\data.csv");

        var filteredData = appsManager.filterByColumn("DeveloperName", "Supercell");

        displayFilteredApps(filteredData);

        filteredData = appsManager.filterByColumn("DeveloperName", "Match 3 Fun Games");

        displayFilteredApps(filteredData);


        var filterFreeApps = appsManager.filterFreeApps();

        displayFilteredApps(filterFreeApps);

    }


    public static void displayFilteredApps(List<String[]> filteredApps) {
        filteredApps.stream()
                .forEach(line -> {
                    System.out.println("Title: " + line[AppsManager.Headers.Title.index]);
                });

        System.out.println();
    }
}