//package gui;
//
//import backend.*;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class ScraperTask implements Runnable {
//    private final String query;
//    private final Settings settings;
//    private final DataStorage dataStorage;
//    private final VisitedSitesManager visitedSitesManager;
//    private final ImageHandler imageHandler;
//    private final ObservableList<ScrapedData> scrapedDataList;
//    private final AtomicBoolean running = new AtomicBoolean(false);
//
//    public ScraperTask(String query, Settings settings, DataStorage dataStorage, VisitedSitesManager visitedSitesManager, ImageHandler imageHandler, ObservableList<ScrapedData> scrapedDataList) {
//        this.query = query;
//        this.settings = settings;
//        this.dataStorage = dataStorage;
//        this.visitedSitesManager = visitedSitesManager;
//        this.imageHandler = imageHandler;
//        this.scrapedDataList = scrapedDataList;
//    }
//
//    @Override
//    public void run() {
//        running.set(true);
//        Scraper scraper = new Scraper(settings, dataStorage, visitedSitesManager, imageHandler) {
//            @Override
//            public void onImageScraped(String pageUrl, String imgUrl, String altText) {
//                Platform.runLater(() -> {
//                    scrapedDataList.add(new ScrapedData(pageUrl, imgUrl, altText));
//                    dataStorage.addData(pageUrl, imgUrl, altText);
//                });
//            }
//        };
//        scraper.startScraping(query);
//        running.set(false);
//    }
//
//    public void stop() {
//        running.set(false);
//    }
//}
