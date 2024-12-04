//samplecontroller.java
package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class SampleController implements Initializable {

    @FXML
    private TextArea messageField;

    @FXML
    private ChoiceBox<String> Drop;
    private String[] choices = {"Option 1", "Option 2"};

    @FXML
    private Button SelectBtn;

    @FXML
    private Button DeleteBtn;

    @FXML
    private Button startScanBtn;
    @FXML
    private Button stopScanBtn;

    @FXML
    private AnchorPane dronecanvas; // The new AnchorPane for drone animation

    private Canvas canvas; // Dynamically added canvas for animation
    private GraphicsContext gc;

    // Drone image and movement variables
    private Image droneImage;
    private double droneX = 50;
    private double droneY = 50;
    private double step = 2;

    private double canvasWidth, canvasHeight;

    // Direction control
    private String direction = "RIGHT";

    private AnimationTimer animationTimer;

    // Initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate ChoiceBox
        Drop.getItems().addAll(choices);

        // Add a Canvas to the dronecanvas
        canvas = new Canvas(dronecanvas.getPrefWidth(), dronecanvas.getPrefHeight());
        dronecanvas.getChildren().add(canvas);

        // Get GraphicsContext
        gc = canvas.getGraphicsContext2D();

        // Set canvas dimensions
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        // Load the drone image
        droneImage = new Image(getClass().getResource("drone.jpg").toExternalForm());

        // Draw initial labels
        drawLabels();
    }

    // Draw text labels on the canvas
    private void drawLabels() {
        gc.setFill(Color.BLACK); // Set text color to black
        gc.fillText("Cow", 100, 100);           // Position (x=100, y=100)
        gc.fillText("Milk Storage", 300, 200); // Position (x=300, y=200)
        gc.fillText("Livestock Area", 200, 400); // Position (x=200, y=400)
    }

    // Start animation timer
    private void startAnimation() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveDrone();
            }
        };
        animationTimer.start();
    }

    // Stop animation timer
    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    // Move drone within the boundaries of dronecanvas
    private void moveDrone() {
        switch (direction) {
            case "RIGHT":
                droneX += step;
                if (droneX + 50 >= canvasWidth) {
                    direction = "DOWN";
                }
                break;
            case "DOWN":
                droneY += step;
                if (droneY + 50 >= canvasHeight) {
                    direction = "LEFT";
                }
                break;
            case "LEFT":
                droneX -= step;
                if (droneX <= 0) {
                    direction = "UP";
                }
                break;
            case "UP":
                droneY -= step;
                if (droneY <= 0) {
                    direction = "RIGHT";
                }
                break;
        }

        // Clear and redraw the canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        drawLabels(); // Redraw labels after clearing
        gc.drawImage(droneImage, droneX, droneY, 50, 50);
    }

    // Start Scan Button Action
    public void startScanAct(ActionEvent event) {
        startAnimation();
        messageField.appendText("Scanning started...\n");
    }

    // Stop Scan Button Action
    public void stopScanAct(ActionEvent event) {
        stopAnimation();
        messageField.appendText("Scanning stopped.\n");
    }

    // Select Button Action
    public void selectAct(ActionEvent event) {
        String selectedOption = Drop.getValue();
        messageField.appendText(selectedOption + " Selected\n");
    }

    // Delete Button Action
    public void deleteAct(ActionEvent event) {
        String selectedOption = Drop.getValue();
        messageField.appendText(selectedOption + " Deleted\n");
    }
}