package backend;

import java.util.Scanner;

import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args) throws CsvException {
        Settings settings = new Settings();
        DataStorage dataStorage = new DataStorage(settings.getDataFilePath());
        VisitedSitesManager visitedSitesManager = new VisitedSitesManager(settings.getVisitedSitesFilePath());
        ImageHandler imageHandler = new ImageHandler(settings.getImagesFolderPath());
        
        dataStorage.loadExistingData();
        visitedSitesManager.loadVisitedSites();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter search query:");
        String query = scanner.nextLine();
        
        Scraper scraper = new Scraper(settings, dataStorage, visitedSitesManager, imageHandler);
        scraper.startScraping(query);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dataStorage.saveData();
            visitedSitesManager.saveVisitedSites();
        }));
    }
}
