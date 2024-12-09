package application;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Duration;

public class Controller {

    @FXML
    private AnchorPane dashankr, cropankr, messageBox;
    @FXML
    private Button startScanButton, stopScanButton, addItemBtn, deleteItemBtn;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView droneImage;
    @FXML
    private TreeView<String> folderTree;

    private boolean isScanning = false;
    private Timeline timeline;

    public void initialize() {
        // Set the drone image and make sure it's visible
        droneImage.setImage(new javafx.scene.image.Image("file:droneimage.jpg")); // Ensure correct path

        // Handle TreeView item selection
        folderTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleTreeSelection(newValue);
        });

        // Start Scan Button Action
        startScanButton.setOnAction(event -> startScanning());

        // Stop Scan Button Action
        stopScanButton.setOnAction(event -> stopScanning());

        // Add Item Button Action (custom logic)
        addItemBtn.setOnAction(event -> addItem());

        // Delete Item Button Action (custom logic)
        deleteItemBtn.setOnAction(event -> deleteItem());
    }

    private void handleTreeSelection(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            String selectedValue = selectedItem.getValue();
            messageLabel.setText("Message: Selected " + selectedValue);
        }
    }

    private void startScanning() {
        // Update message
        messageLabel.setText("Message: Drone scanning...");

        // Create zig-zag scan animation
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> droneImage.setLayoutX(50)),
            new KeyFrame(Duration.seconds(1), e -> droneImage.setLayoutY(100)),
            new KeyFrame(Duration.seconds(2), e -> droneImage.setLayoutX(250)),
            new KeyFrame(Duration.seconds(3), e -> droneImage.setLayoutY(200)),
            new KeyFrame(Duration.seconds(4), e -> droneImage.setLayoutX(50)),
            new KeyFrame(Duration.seconds(5), e -> droneImage.setLayoutY(300))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();  // Play the animation

        isScanning = true;
    }

    private void stopScanning() {
        if (timeline != null && isScanning) {
            timeline.stop();  // Stop the timeline animation
            messageLabel.setText("Message: Drone stopped.");
        } else {
            messageLabel.setText("Message: No active scan to stop.");
        }

        isScanning = false;

        // Optionally reset the drone's position
        droneImage.setLayoutX(50);
        droneImage.setLayoutY(50);
    }


    private void addItem() {
        // Add your logic here for adding an item (e.g., add an object to the UI or database)
        messageLabel.setText("Message: Item Added.");
    }

    private void deleteItem() {
        // Add your logic here for deleting an item (e.g., remove an object from the UI or database)
        messageLabel.setText("Message: Item Deleted.");
    }
}