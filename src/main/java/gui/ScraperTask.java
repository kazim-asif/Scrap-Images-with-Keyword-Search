
package gui;

import backend.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScraperTask implements Runnable {
    private final String query;
    private final Settings settings;
    private final DataStorage dataStorage;
    private final VisitedSitesManager visitedSitesManager;
    private final ImageHandler imageHandler;
    private final ObservableList<ScrapedData> scrapedDataList;
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final ExecutorService executorService;

    public ScraperTask(String query, Settings settings, DataStorage dataStorage, VisitedSitesManager visitedSitesManager, ImageHandler imageHandler, ObservableList<ScrapedData> scrapedDataList) {
        this.query = query;
        this.settings = settings;
        this.dataStorage = dataStorage;
        this.visitedSitesManager = visitedSitesManager;
        this.imageHandler = imageHandler;
        this.scrapedDataList = scrapedDataList;
        this.executorService = Executors.newFixedThreadPool(4); // Number of threads can be adjusted
    }

    @Override
    public void run() {
        running.set(true);
        Scraper scraper = new Scraper(settings, dataStorage, visitedSitesManager, imageHandler) {
            @Override
            public void onImageScraped(String pageUrl, String imgUrl, String altText) {
                Platform.runLater(() -> {
                    scrapedDataList.add(new ScrapedData(pageUrl, imgUrl, altText));
                    executorService.submit(() -> dataStorage.addData(pageUrl, imgUrl, altText));
                });
            }

            public void onSiteVisited(String url) {
                executorService.submit(() -> visitedSitesManager.addVisitedSite(url));
            }
        };
        scraper.startScraping(query);

        // Shut down the executor service when scraping is done
//        while (running.get() && !executorService.isTerminated()) {
//            // Wait for all tasks to complete
//        }
//        executorService.shutdown();
        
        // Wait for all tasks to complete and then shut down the executor service
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        } finally {
            running.set(false);
        }
        
        
    }

    public void stop() {
        running.set(false);
        executorService.shutdownNow();
    }
}
