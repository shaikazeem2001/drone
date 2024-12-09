package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class singletonDashboard {
    private static singletonDashboard instance;
    private Stage primaryStage;
    private singletonDashboard() {
    }

    public static singletonDashboard getInstance() {
        if (instance == null) {
            instance = new singletonDashboard();
        }
        return instance;
    }
    public void initialize(Stage stage) {
        if (this.primaryStage == null) {
            this.primaryStage = stage;
            setupStage();
        }
    }

    private void setupStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Farm Dashboard");
            primaryStage.setResizable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        if (primaryStage != null) {
            primaryStage.show();
        }
    }
}