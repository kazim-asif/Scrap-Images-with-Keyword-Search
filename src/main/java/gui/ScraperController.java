package gui;

import backend.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.opencsv.exceptions.CsvException;

public class ScraperController {
    private TextField queryField;
    private Button startButton;
    private Button stopButton;
    private TableView<ScrapedData> tableView;
    private TableColumn<ScrapedData, String> pageUrlColumn;
    private TableColumn<ScrapedData, String> imgUrlColumn;
    private TableColumn<ScrapedData, String> altTextColumn;

    private ObservableList<ScrapedData> scrapedDataList;
    private ExecutorService executorService;
    private ScraperTask scraperTask;

    public void initialize(TextField queryField, Button startButton, Button stopButton, TableView<ScrapedData> tableView, TableColumn<ScrapedData, String> pageUrlColumn, TableColumn<ScrapedData, String> imgUrlColumn, TableColumn<ScrapedData, String> altTextColumn) {
        this.queryField = queryField;
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.tableView = tableView;
        this.pageUrlColumn = pageUrlColumn;
        this.imgUrlColumn = imgUrlColumn;
        this.altTextColumn = altTextColumn;

        scrapedDataList = FXCollections.observableArrayList();
        tableView.setItems(scrapedDataList);
        pageUrlColumn.setCellValueFactory(data -> data.getValue().pageUrlProperty());
        imgUrlColumn.setCellValueFactory(data -> data.getValue().imgUrlProperty());
        altTextColumn.setCellValueFactory(data -> data.getValue().altTextProperty());

        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> startScraping());
        stopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stopScraping());
        stopButton.setDisable(true);
    }

    private void startScraping() {
        String query = queryField.getText();
        if (query.isEmpty()) {
            showAlert("Query cannot be empty!");
            return;
        }

        startButton.setDisable(true);
        stopButton.setDisable(false);
        scrapedDataList.clear();

        Settings settings = new Settings();
        DataStorage dataStorage = new DataStorage(settings.getDataFilePath());
        VisitedSitesManager visitedSitesManager = new VisitedSitesManager(settings.getVisitedSitesFilePath());
        ImageHandler imageHandler = new ImageHandler(settings.getImagesFolderPath());

        try {
			dataStorage.loadExistingData();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        visitedSitesManager.loadVisitedSites();

        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        scraperTask = new ScraperTask(query, settings, dataStorage, visitedSitesManager, imageHandler, scrapedDataList);
        executorService.submit(scraperTask);
    }

    private void stopScraping() {
        if (scraperTask != null) {
            scraperTask.stop();
        }
        stopButton.setDisable(true);
        startButton.setDisable(false);
        executorService.shutdown();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
