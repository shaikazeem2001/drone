package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import patterns.FarmItem;
import patterns.ItemContainer;
import patterns.Weather;
import patterns.FarmComponent;

public class MainController implements Initializable {

    @FXML
    private ImageView droneImageView;

    @FXML
    private AnchorPane screen2;

    @FXML
    private AnchorPane screen1;

    @FXML
    private Button scanFarmButton;
    
    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextArea statusTextArea;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;
    
    @FXML
    private TextField priceField;

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    private Button addsTheItem;
    
    @FXML
    private Button changeItemName;
    
    @FXML
    private Button changeItemContainerName; 

    @FXML
    private TextField nameField;

    @FXML
    private Button handleAddItem;

    @FXML
    private Button handlesItemContainer;
    
    @FXML
    private Button deleteItem;
    
    @FXML
    private Button changePrice;
    
    @FXML
    private Button deleteItemContainer;
    
    @FXML
    private TreeViewManager treeViewManager;

    @FXML
    private Button checkWeatherButton;
    private final double commandCenterX = 0;
    private final double commandCenterY = 0;
    private ItemContainer rootContainer = new ItemContainer("Farm Layout", 0, 0, 800, 600);
    private FarmLayoutManager layoutManager;
    @FXML
    private Pane layoutPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layoutManager = new FarmLayoutManager(rootContainer, screen1, statusTextArea);
        treeViewManager = new TreeViewManager(treeView, layoutManager, rootContainer);
        droneImageView.setImage(new Image("file:src/application/drone.png")); 
        droneImageView.setX(commandCenterX);
        droneImageView.setY(commandCenterY);
        TreeItem<String> rootItem = new TreeItem<>("Root");
        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);
        scanFarmButton.setOnAction(event -> handleScanFarm());
        checkWeatherButton.setOnAction(event -> handleCheckWeather());
    }
    @FXML
    public void handleAddItem() {
        try {
            String name = nameField.getText().trim();
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double width = Double.parseDouble(widthField.getText());
            double height = Double.parseDouble(heightField.getText());
            double price = Double.parseDouble(priceField.getText());
            if (name.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            } 
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            treeViewManager.addItem(name, x, y, width, height, price, selectedNode);
            statusTextArea.appendText("Added item: " + name + " location: " + x + "and" + y + "dimension: "+ width + "and" + height + " with price $" + price + "\n");
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (IllegalArgumentException e) {
            statusTextArea.appendText(e.getMessage() + "\n");
        }
    }
    @FXML
    public void handleChangePrice() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();

            if (selectedNode == null) {
                statusTextArea.appendText("Error: No item selected to update price.\n");
                return;
            }

            double newPrice = Double.parseDouble(priceField.getText());
            if (newPrice < 0) {
                statusTextArea.appendText("Error: Price must be non-negative.\n");
                return;
            }

            // Find the corresponding FarmComponent
            FarmComponent componentToUpdate = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToUpdate instanceof FarmItem) {
                ((FarmItem) componentToUpdate).setPrice(newPrice);
                statusTextArea.appendText("Updated price for item: " + selectedNode.getValue() + " to $" + newPrice + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item. Cannot update price.\n");
            }
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        }
    }
    @FXML
    public void handleAddItemContainer() {
        try {
            String name = nameField.getText().trim();
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double width = Double.parseDouble(widthField.getText());
            double height = Double.parseDouble(heightField.getText());

            if (name.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            }
            else if(!priceField.getText().trim().isEmpty()) {
               	statusTextArea.appendText("Error: You Cannot add the price value in the Item Container.\n");
                return;
            }
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            treeViewManager.addItemContainer(name, x, y, width, height, selectedNode);
            statusTextArea.appendText("Added item Container: " +" "+ name +" "+ " location: " + x + "and " + y + "dimension: "+ width + " and " + height + "\n");
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (IllegalArgumentException e) {
            statusTextArea.appendText(e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleChangeItemName() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();

            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for renaming.\n");
                return;
            }

            String oldName = selectedNode.getValue();
            String newName = nameField.getText().trim();
            if (newName.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            }
            FarmComponent componentToRename = treeViewManager.findComponentByName(rootContainer, oldName);
            if (componentToRename instanceof FarmItem) {
                componentToRename.setName(newName);
                selectedNode.setValue(newName);
                layoutManager.updateComponentVisualization(componentToRename, oldName);
                statusTextArea.appendText("Renamed item to: " + newName + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item. Cannot rename.\n");
            }
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleChangeItemContainerName() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for renaming.\n");
                return;
            }
            String oldName = selectedNode.getValue();
            String newName = nameField.getText().trim();
            if (newName.isEmpty()) {
                statusTextArea.appendText("Error: Name cannot be empty.\n");
                return;
            }
            FarmComponent componentToRename = treeViewManager.findComponentByName(rootContainer, oldName);
            if (componentToRename instanceof ItemContainer) {
                componentToRename.setName(newName);
                selectedNode.setValue(newName);
                layoutManager.updateComponentVisualization(componentToRename, oldName);
                statusTextArea.appendText("Renamed item container to: " + newName + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not a container. Cannot rename.\n");
            }
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    public void handleDeleteItem() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for deletion.\n");
                return;
            }
            FarmComponent componentToDelete = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToDelete instanceof FarmItem) {
                treeViewManager.deleteItem(selectedNode);
                statusTextArea.appendText("Deleted item: " + selectedNode.getValue() + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item. Cannot perform 'Delete Item' command.\n");
            }
        } catch (IllegalArgumentException e) {
            statusTextArea.appendText(e.getMessage() + "\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleDeleteItemContainer() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();

            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for deletion.\n");
                return;
            }
            FarmComponent componentToDelete = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToDelete instanceof ItemContainer) {
                treeViewManager.deleteItemContainer(selectedNode);
                statusTextArea.appendText("Deleted item container: " + selectedNode.getValue() + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item container. Cannot perform 'Delete Item Container' command.\n");
            }
        } catch (IllegalArgumentException e) {
            statusTextArea.appendText(e.getMessage() + "\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleChangeItemDimension() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for dimension change.\n");
                return;
            }
            double newWidth = Double.parseDouble(widthField.getText());
            double newHeight = Double.parseDouble(heightField.getText());
            if (newWidth <= 0 || newHeight <= 0) {
                statusTextArea.appendText("Error: Dimensions must be greater than zero.\n");
                return;
            }
            FarmComponent componentToUpdate = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToUpdate instanceof FarmItem) {
                componentToUpdate.setWidth(newWidth);
                componentToUpdate.setHeight(newHeight);
                layoutManager.updateComponentDimensions(componentToUpdate);
                statusTextArea.appendText("Changed dimensions for item: " + selectedNode.getValue() + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item. Cannot change dimensions.\n");
            }
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleChangeItemContainerDimension() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();

            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for dimension change.\n");
                return;
            }

            double newWidth = Double.parseDouble(widthField.getText());
            double newHeight = Double.parseDouble(heightField.getText());

            if (newWidth <= 0 || newHeight <= 0) {
                statusTextArea.appendText("Error: Dimensions must be greater than zero.\n");
                return;
            }
            FarmComponent componentToUpdate = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToUpdate instanceof ItemContainer) {
                componentToUpdate.setWidth(newWidth);
                componentToUpdate.setHeight(newHeight);
                layoutManager.updateComponentDimensions(componentToUpdate);
                statusTextArea.appendText("Changed dimensions for item container: " + selectedNode.getValue() + "\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not a container. Cannot change dimensions.\n");
            }
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
    
    @FXML
    public void handleChangeItemLocation() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for location change.\n");
                return;
            }
            double newX = Double.parseDouble(xField.getText());
            double newY = Double.parseDouble(yField.getText());

            // Validate input
            if (newX < 0 || newY < 0) {
                statusTextArea.appendText("Error: Location coordinates must be non-negative.\n");
                return;
            }
            FarmComponent componentToUpdate = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToUpdate instanceof FarmItem) {
                componentToUpdate.setX(newX);
                componentToUpdate.setY(newY);
                layoutManager.updateComponentLocation(componentToUpdate);
                statusTextArea.appendText("Changed location for item: " + selectedNode.getValue() + " to (" + newX + ", " + newY + ")\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not an item. Cannot change location.\n");
            }
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    public void handleChangeItemContainerLocation() {
        try {
            TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
            if (selectedNode == null) {
                statusTextArea.appendText("Error: No node selected for location change.\n");
                return;
            }
            double newX = Double.parseDouble(xField.getText());
            double newY = Double.parseDouble(yField.getText());
            if (newX < 0 || newY < 0) {
                statusTextArea.appendText("Error: Location coordinates must be non-negative.\n");
                return;
            }
            FarmComponent componentToUpdate = treeViewManager.findComponentByName(rootContainer, selectedNode.getValue());
            if (componentToUpdate instanceof ItemContainer) {
                componentToUpdate.setX(newX);
                componentToUpdate.setY(newY);
                layoutManager.updateComponentLocation(componentToUpdate);
                statusTextArea.appendText("Changed location for item container: " + selectedNode.getValue() + " to (" + newX + ", " + newY + ")\n");
            } else {
                statusTextArea.appendText("Error: Selected node is not a container. Cannot change location.\n");
            }
        } catch (NumberFormatException e) {
            statusTextArea.appendText("Error: Invalid numeric input.\n");
        } catch (Exception e) {
            statusTextArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }   
    
    @FXML
    public void handleScanFarm() {
        startDroneScan();
    }
    public void startDroneScan() {
        statusTextArea.appendText("Drone has started scanning the farm.\n");

        SequentialTransition sequentialTransition = new SequentialTransition();
        double stepSize = 50.0;
        double visualizationWidth = screen1.getPrefWidth();
        double visualizationHeight = screen1.getPrefHeight();
        double startX = 0.0;
        double startY = 0.0;
        droneImageView.setX(startX);
        droneImageView.setY(startY);
        int rows = (int) (visualizationHeight / stepSize);
        int cols = (int) (visualizationWidth / stepSize);
        for (int row = 0; row < rows; row++) {
            TranslateTransition moveRow = new TranslateTransition(Duration.seconds(2), droneImageView);
            moveRow.setByX((row % 2 == 0 ? 1 : -1) * (cols - 1) * stepSize);
            sequentialTransition.getChildren().add(moveRow);
            if (row < rows - 1) {
                TranslateTransition moveDown = new TranslateTransition(Duration.seconds(1), droneImageView);
                moveDown.setByY(stepSize);
                sequentialTransition.getChildren().add(moveDown);
            }
        }
        TranslateTransition returnToCommandCenter = new TranslateTransition(Duration.seconds(2), droneImageView);
        returnToCommandCenter.setToX(startX - droneImageView.getX());
        returnToCommandCenter.setToY(startY - droneImageView.getY());
        sequentialTransition.getChildren().add(returnToCommandCenter);
        sequentialTransition.setOnFinished(event -> {
            statusTextArea.appendText("Drone has finished scanning the farm.\n");
        });
        sequentialTransition.play();
    }
    
    @FXML
    public void handleVisitNode() {
        TreeItem<String> selectedNode = treeView.getSelectionModel().getSelectedItem();
        if (selectedNode == null) {
            statusTextArea.appendText("Error: No node selected for visiting.\n");
            return;
        }
        String nodeName = selectedNode.getValue();
        Node targetNode = null;
        for (Node node : screen1.getChildren()) { 
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                Label label = (Label) stackPane.getChildren().stream()
                        .filter(child -> child instanceof Label)
                        .map(child -> (Label) child)
                        .findFirst()
                        .orElse(null);
                if (label != null && label.getText().equals(nodeName)) {
                    targetNode = stackPane;
                    break;
                }
            }
        }

        if (targetNode == null) {
            statusTextArea.appendText("Error: Could not find visualization for node: " + nodeName + "\n");
            return;
        }
        double targetX = targetNode.getLayoutX();
        double targetY = targetNode.getLayoutY();
        animateDrone(targetX, targetY);
    }
    private void animateDrone(double targetX, double targetY) {
        double startX = droneImageView.getLayoutX();
        double startY = droneImageView.getLayoutY();
        TranslateTransition goToTarget = new TranslateTransition(Duration.seconds(2), droneImageView);
        goToTarget.setToX(targetX - startX);
        goToTarget.setToY(targetY - startY);
        PauseTransition pauseAtTarget = new PauseTransition(Duration.seconds(1));
        TranslateTransition returnToStart = new TranslateTransition(Duration.seconds(2), droneImageView);
        returnToStart.setToX(0);
        returnToStart.setToY(0); 
        SequentialTransition droneAnimation = new SequentialTransition(goToTarget, pauseAtTarget, returnToStart);
        droneAnimation.setOnFinished(e -> statusTextArea.appendText("Drone returned to start.\n"));
        droneAnimation.play();
    }
    
    @FXML
    public void handleCheckWeather() {
        Weather weather = new Weather();
        weather.checkWeather();
        StringBuilder report = new StringBuilder();
        report.append("Weather Report:\n");
        report.append("Temperature: ").append(weather.getTemperature()).append("°C\n");
        report.append("Humidity: ").append(weather.getHumidity()).append("%\n");
        report.append("Forecast: ").append(weather.getForecast()).append("\n\n");
        report.append("Alert for Farmer:\n");
        report.append(weather.alertFarmer());
        statusTextArea.appendText(report.toString() + "\n");
    }
}