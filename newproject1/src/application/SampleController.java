package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SampleController {

    @FXML
    private AnchorPane ankordash, maindash, messagedash, cropankor;
    @FXML
    private ImageView droneImage;
    @FXML
    private Button startScanButton, stopScanButton, changeXButton, changeYButton, changeHeightButton, changeWidthButton;
    @FXML
    private TreeView<String> fileTree;
    @FXML
    private TextArea messageArea;

    private boolean scanning = false;
    private double maxWidth;
    private double maxHeight;
    private Map<String, Position> farmItems = new HashMap<>();

    @FXML
    public void initialize() {
        ankordash.setStyle("-fx-background-color: white;");
        maindash.setStyle("-fx-background-color: white;");
        messagedash.setStyle("-fx-background-color: white;");
        cropankor.setStyle("-fx-background-color: white;");

        maxWidth = cropankor.getPrefWidth();
        maxHeight = cropankor.getPrefHeight();

        try {
            File imageFile = new File("./src/application/droneimage.jpg");
            if (imageFile.exists()) {
                Image droneImageFile = new Image(imageFile.toURI().toString());
                droneImage.setImage(droneImageFile);
                droneImage.setFitWidth(100);
                droneImage.setFitHeight(100);
                droneImage.setPreserveRatio(true);
            } else {
                logMessage("Drone image not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logMessage("Error loading drone image.");
        }

        startScanButton.setOnAction(e -> {
            logMessage("Drone started scanning.");
            startScan();
        });

        stopScanButton.setOnAction(e -> {
            logMessage("Drone stopped scanning.");
            stopScan();
        });

        changeXButton.setOnAction(e -> {
            logMessage("Change X button clicked.");
            showInputDialog("Change X", true);
        });

        changeYButton.setOnAction(e -> {
            logMessage("Change Y button clicked.");
            showInputDialog("Change Y", false);
        });

        changeHeightButton.setOnAction(e -> logMessage("Change Height button clicked."));
        changeWidthButton.setOnAction(e -> logMessage("Change Width button clicked."));

        initializeFileTree();
    }

    private void initializeFileTree() {
        TreeItem<String> root = new TreeItem<>("Root");
        TreeItem<String> barn = new TreeItem<>("Barn");
        TreeItem<String> cow = new TreeItem<>("Cow");
        TreeItem<String> milkStorage = new TreeItem<>("Milk Storage");
        TreeItem<String> liveStorage = new TreeItem<>("Live Storage");
        TreeItem<String> storeRoom = new TreeItem<>("Store Room");
        barn.getChildren().addAll(cow, milkStorage, liveStorage, storeRoom);

        TreeItem<String> crop = new TreeItem<>("Crop");

        root.getChildren().addAll(barn, crop);

        fileTree.setRoot(root);
        fileTree.setShowRoot(true);

        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedItem = newValue.getValue();
                showItemDialog(selectedItem);
            }
        });
    }

    private void showInputDialog(String prompt, boolean isX) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(prompt);
        dialog.setHeaderText("Enter the " + prompt + " value");
        dialog.setContentText("Value:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            try {
                if (isX) {
                    updatePosition(Double.parseDouble(value), droneImage.getLayoutY());
                } else {
                    updatePosition(droneImage.getLayoutX(), Double.parseDouble(value));
                }
            } catch (NumberFormatException e) {
                logMessage("Invalid input for " + prompt);
            }
        });
    }

    private void updatePosition(double x, double y) {
        Platform.runLater(() -> {
            droneImage.setLayoutX(x);
            droneImage.setLayoutY(y);
        });
    }

    private void showItemDialog(String item) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manage Item");
        dialog.setHeaderText("Manage the selected item: " + item);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField(item);
        nameField.setPromptText("Update name");
        TextField xAxisField = new TextField("0");
        xAxisField.setPromptText("X-axis");
        TextField yAxisField = new TextField("0");
        yAxisField.setPromptText("Y-axis");

        grid.add(new Label("Update Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("X-axis:"), 0, 1);
        grid.add(xAxisField, 1, 1);
        grid.add(new Label("Y-axis:"), 0, 2);
        grid.add(yAxisField, 1, 2);

        ButtonType addButton = new ButtonType("Add Item", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(addButton);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String newName = nameField.getText();
                double x = Double.parseDouble(xAxisField.getText());
                double y = Double.parseDouble(yAxisField.getText());
                addItemToFarm(newName, x, y);
                logMessage(newName + " added at position (" + x + ", " + y + ").");
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addItemToFarm(String name, double x, double y) {
        if (!farmItems.containsKey(name)) {
            farmItems.put(name, new Position(x, y));
            createColorBlock(x, y, Color.BLUE, name);
        }
    }

    private void createColorBlock(double x, double y, Color color, String text) {
        Rectangle colorBlock = new Rectangle(x, y, 50, 50);
        colorBlock.setFill(color);
        colorBlock.setStroke(Color.BLACK);

        Text colorText = new Text(x, y + 60, text);
        colorText.setFill(Color.BLACK);

        cropankor.getChildren().addAll(colorBlock, colorText);
    }

    private void deleteItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete Item");
        dialog.setHeaderText("Delete the selected item");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter item name");
        TextField xAxisField = new TextField();
        xAxisField.setPromptText("X-axis");
        TextField yAxisField = new TextField();
        yAxisField.setPromptText("Y-axis");

        grid.add(new Label("Delete Item:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("X-axis:"), 0, 1);
        grid.add(xAxisField, 1, 1);
        grid.add(new Label("Y-axis:"), 0, 2);
        grid.add(yAxisField, 1, 2);

        ButtonType deleteButton = new ButtonType("Delete Item", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(deleteButton);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButton) {
                String name = nameField.getText();
                double x = Double.parseDouble(xAxisField.getText());
                double y = Double.parseDouble(yAxisField.getText());
                deleteItemFromFarm(name, x, y);
                logMessage(name + " deleted from farm at position (" + x + ", " + y + ").");
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void showItemDialog(String item) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Manage Item");
        dialog.setHeaderText("Manage the selected item: " + item);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Fields for item properties
        TextField nameField = new TextField(item);
        nameField.setPromptText("Update name");
        TextField newNameField = new TextField();
        newNameField.setPromptText("New name");
        TextField xAxisField = new TextField("0");
        xAxisField.setPromptText("X-axis");
        TextField yAxisField = new TextField("0");
        yAxisField.setPromptText("Y-axis");

        // Adding fields to the grid
        grid.add(new Label("Current Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("New Name:"), 0, 1);
        grid.add(newNameField, 1, 1);
        grid.add(new Label("X-axis:"), 0, 2);
        grid.add(xAxisField, 1, 2);
        grid.add(new Label("Y-axis:"), 0, 3);
        grid.add(yAxisField, 1, 3);

        ButtonType addButton = new ButtonType("Add Item", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(addButton);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String currentName = nameField.getText();
                String newName = newNameField.getText();
                double x = Double.parseDouble(xAxisField.getText());
                double y = Double.parseDouble(yAxisField.getText());

                if (!newName.isEmpty()) {
                    addItemToFarm(newName, x, y); // Add item with updated name
                    logMessage(currentName + " updated to " + newName + " and added at position (" + x + ", " + y + ").");
                } else {
                    logMessage("New name cannot be empty.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void deleteItemFromFarm(String name, double x, double y) {
        Position position = farmItems.get(name);
        if (position != null) {
            farmItems.remove(name);
            cropankor.getChildren().removeIf(node -> {
                if (node instanceof Rectangle) {
                    Rectangle rect = (Rectangle) node;
                    return rect.getX() == position.x && rect.getY() == position.y;
                } else if (node instanceof Text) {
                    Text text = (Text) node;
                    return text.getText().equals(name) && text.getX() == position.x && text.getY() == position.y + 60;
                }
                return false;
            });
        }
    }


    private void logMessage(String message) {
        Platform.runLater(() -> messageArea.appendText(message + "\n"));
    }

    private Timeline scanTimeline;

    private void startScan() {
        if (scanTimeline != null && scanTimeline.getStatus() == Animation.Status.RUNNING) {
            logMessage("Scanning is already in progress.");
            return;
        }

        droneImage.setFitWidth(50);

        scanTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            double x = droneImage.getLayoutX();
            double y = droneImage.getLayoutY();
            double step = 10;

            // Move the drone vertically
            y += step;

            // Check boundaries and reset
            if (y > maxHeight - droneImage.getFitHeight()) {
                y = 0; // Reset to top
                x += 50; // Move horizontally
                if (x > maxWidth - droneImage.getFitWidth()) {
                    x = 0; // Reset to the leftmost position
                }
            }

            updatePosition(x, y);
        }));
        scanTimeline.setCycleCount(Animation.INDEFINITE);
        scanTimeline.play();
        logMessage("Scanning started.");
    }

    private void stopScan() {
        if (scanTimeline != null) {
            scanTimeline.stop();
            logMessage("Scanning has been stopped.");
        }
    }



    private void performScan() {
        double x = droneImage.getLayoutX();
        double y = droneImage.getLayoutY();
        double step = 10;

        while (scanning) {
            try {
                Thread.sleep(100);
                x += step;
                if (x > maxWidth - droneImage.getFitWidth() || x < 0) {
                    step = -step;
                    y += 50;
                    if (y > maxHeight - droneImage.getFitHeight()) {
                        y = 0;
                    }
                }
                updatePosition(x, y);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Position {
    double x, y;

    Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
}