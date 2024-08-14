package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ScraperApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Setting up the UI
        ScraperController controller = new ScraperController();

        // Creating the layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        HBox inputBox = new HBox(10);
        Label queryLabel = new Label("Search Query:");
        TextField queryField = new TextField();
        queryField.setPromptText("Enter search query");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        inputBox.getChildren().addAll(queryLabel, queryField, startButton, stopButton);

        TableView<ScrapedData> tableView = new TableView<>();
        TableColumn<ScrapedData, String> pageUrlColumn = new TableColumn<>("Page URL");
        TableColumn<ScrapedData, String> imgUrlColumn = new TableColumn<>("Image URL");
        TableColumn<ScrapedData, String> altTextColumn = new TableColumn<>("Alt Text");
        pageUrlColumn.setResizable(true);
        imgUrlColumn.setResizable(true);
        altTextColumn.setResizable(true);
        tableView.getColumns().addAll(pageUrlColumn, imgUrlColumn, altTextColumn);

        root.getChildren().addAll(inputBox, tableView);

        // Setting up the scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Web Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initializing controller
        controller.initialize(queryField, startButton, stopButton, tableView, pageUrlColumn, imgUrlColumn, altTextColumn);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
