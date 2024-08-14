

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class Scraper {
//    private static final int MAX_RESULTS = 10;
//    private Settings settings;
//    private DataStorage dataStorage;
//    private VisitedSitesManager visitedSitesManager;
//    private ImageHandler imageHandler;
//
//    public Scraper(Settings settings, DataStorage dataStorage, VisitedSitesManager visitedSitesManager, ImageHandler imageHandler) {
//        this.settings = settings;
//        this.dataStorage = dataStorage;
//        this.visitedSitesManager = visitedSitesManager;
//        this.imageHandler = imageHandler;
//    }
//
//    public void startScraping(String query) {
//        try {
//            String searchUrl = "https://www.google.com/search?q=" + query + "&num=" + MAX_RESULTS;
//            Document doc = Jsoup.connect(searchUrl).userAgent("Mozilla/5.0").get();
//            Elements results = doc.select("a[href]");
//
//            int resultCount = 0;
//
//            for (Element result : results) {
//                if (resultCount >= MAX_RESULTS) {
//                    break;
//                }
//
//                String url = result.absUrl("href");
//                if (!visitedSitesManager.isVisited(url) && url.contains("http")) {
//                    visitedSitesManager.addVisitedSite(url);
//                    scrapePage(url);
//                    resultCount++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void scrapePage(String url) {
//        try {
//            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
//            Elements images = doc.select("img");
//
//            for (Element img : images) {
//                String imgUrl = img.absUrl("src");
//                String altText = ""+img.attr("alt");
//                if (!imgUrl.isEmpty()) {
//                    imageHandler.downloadAndSaveImage(imgUrl, altText);
//                    dataStorage.addData(url, imgUrl, altText);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}








//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class Scraper {
//    private static final int MAX_RESULTS = 50;
//    private Settings settings;
//    private DataStorage dataStorage;
//    private VisitedSitesManager visitedSitesManager;
//    private ImageHandler imageHandler;
//
//    public Scraper(Settings settings, DataStorage dataStorage, VisitedSitesManager visitedSitesManager, ImageHandler imageHandler) {
//        this.settings = settings;
//        this.dataStorage = dataStorage;
//        this.visitedSitesManager = visitedSitesManager;
//        this.imageHandler = imageHandler;
//    }
//
//    public void startScraping(String query) {
//        try {
//            String searchUrl = "https://www.google.com/search?q=" + query + "&tbm=isch";
//            Document doc = Jsoup.connect(searchUrl).userAgent("Mozilla/5.0").get();
//            Elements results = doc.select("a[href]");
//
//            int resultCount = 0;
//
//            for (Element result : results) {
//                if (resultCount >= MAX_RESULTS) {
//                    break;
//                }
//
//                String url = result.absUrl("href");
//                if (!visitedSitesManager.isVisited(url)) {
//                    visitedSitesManager.addVisitedSite(url);
//                    int newResults = scrapePage(url);
//                    resultCount += newResults;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private int scrapePage(String url) {
//        int count = 0;
//        try {
//            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
//            Elements images = doc.select("img");
//
//            for (Element img : images) {
//                if (count >= MAX_RESULTS) {
//                    break;
//                }
//
//                String imgUrl = img.absUrl("src");
//                String altText = img.attr("alt");
//                if (!imgUrl.isEmpty() && !altText.isEmpty()) {
//                    imageHandler.downloadAndSaveImage(imgUrl, altText);
//                    dataStorage.addData(url, altText);
//                    count++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//}
