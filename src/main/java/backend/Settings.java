package backend;

public class Settings {
    private String dataFilePath;
    private String visitedSitesFilePath;
    private String imagesFolderPath;

    public Settings() {
        // Set the paths manually
        this.dataFilePath = "src\\main\\java\\data\\data.csv";
        this.visitedSitesFilePath = "src\\main\\java\\data\\visited_sites.txt";
        this.imagesFolderPath = "src\\main\\java\\images";
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public String getVisitedSitesFilePath() {
        return visitedSitesFilePath;
    }

    public String getImagesFolderPath() {
        return imagesFolderPath;
    }
}
