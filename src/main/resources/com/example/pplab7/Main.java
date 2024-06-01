import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.File;

public class Main extends Application {

    private TextField directoryPathField;
    private TextField searchField;
    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("File Browser and Search");

        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");
        searchField = new TextField();
        searchField.setPromptText("Enter search phrase");

        resultArea = new TextArea();
        resultArea.setPrefHeight(400);

        Button browseButton = new Button("Browse");
        browseButton.setOnAction(event -> browseDirectory());
        Button searchButton = new Button("Search");

        HBox hBox = new HBox(directoryPathField, browseButton);
        VBox vBox = new VBox(10, hBox, searchField, searchButton, resultArea);

        searchButton.setOnAction(event -> searchFiles());

        Scene scene = new Scene(vBox, 600, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void searchFiles() {
        String directoryPath = directoryPathField.getText();

        if (directoryPath.isEmpty()) {
            resultArea.setText("Please provide a directory path.");
            return;
        }

        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
            return;
        }

        StringBuilder results = new StringBuilder();
        listFilesInDirectory(directory, results);

        resultArea.setText(results.toString());
    }

    private void listFilesInDirectory(File directory, StringBuilder results) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    results.append(file.getName()).append("\n");
                } else if (file.isDirectory()) {
                    listFilesInDirectory(file, results);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}