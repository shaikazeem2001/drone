package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import patterns.FarmComponent;
import patterns.FarmItem;
import patterns.ItemContainer;

public class FarmLayoutManager {
    private final ItemContainer rootContainer;
    private final AnchorPane layoutPane;
    private final TextArea statusTextArea;

    public FarmLayoutManager(ItemContainer rootContainer, AnchorPane layoutPane, TextArea statusTextArea) {
        this.rootContainer = rootContainer;
        this.layoutPane = layoutPane;
        this.statusTextArea = statusTextArea;
    }
    public boolean modifyComponent(String name, double newX, double newY, double newWidth, double newHeight) {
        for (FarmComponent component : rootContainer.getComponents()) {
            if (component.getName().equals(name)) {
                component.setX(newX);
                component.setY(newY);
                component.setWidth(newWidth);
                component.setHeight(newHeight);

                // Update the StackPane visualization
                for (javafx.scene.Node node : layoutPane.getChildren()) {
                    if (node instanceof StackPane) {
                        StackPane stackPane = (StackPane) node;

                        if (stackPane.getChildren().size() > 1 && 
                            stackPane.getChildren().get(1) instanceof Label) {
                            Label label = (Label) stackPane.getChildren().get(1);

                            if (label.getText().equals(name)) {
                       
                                stackPane.setLayoutX(newX);
                                stackPane.setLayoutY(newY);
                                stackPane.setPrefSize(newWidth, newHeight);

                                                                Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
                                rectangle.setWidth(newWidth);
                                rectangle.setHeight(newHeight);

                                System.out.println("Updated Rectangle and Label for Component: " + name);
                                return true; 
                            }
                        }
                    }
                }
            }
        }
        return false; 
    }

    public void addFarmItem(String name, double x, double y, double width, double height, double price) {
        FarmItem newItem = new FarmItem(name, x, y, width, height, price); // Pass the price
        rootContainer.add(newItem);
        visualizeComponentItem(newItem);
        statusTextArea.appendText("Added item: " + name + " at (" + x + ", " + y + "), Price: $" + price + "\n");
    }


    public void addItemContainer(String name, double x, double y, double width, double height) {
        ItemContainer newContainer = new ItemContainer(name, x, y, width, height);
        rootContainer.add(newContainer);
        visualizeComponent(newContainer);
        statusTextArea.appendText("Added container: " + name + " at (" + x + ", " + y + ")\n");
    }
    
    public void visualizeComponentItem(FarmComponent component) {
        Rectangle circle = new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        Label label = new Label(component.getName());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(component.getX());
        stackPane.setLayoutY(component.getY());
        stackPane.setPrefSize(component.getWidth(), component.getHeight());
        stackPane.getChildren().addAll(circle, label);

        layoutPane.getChildren().add(stackPane);

        System.out.println("Visualized Component: " + component.getName());
    }

    public void visualizeComponent(FarmComponent component) {
        Rectangle rectangle = new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);

        Label label = new Label(component.getName());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(component.getX());
        stackPane.setLayoutY(component.getY());
        stackPane.setPrefSize(component.getWidth(), component.getHeight());
        stackPane.getChildren().addAll(rectangle, label);

        layoutPane.getChildren().add(stackPane);

        System.out.println("Visualized Component: " + component.getName());
    }
    
    public void removeComponentVisualization(FarmComponent component) {
     
        layoutPane.getChildren().removeIf(node -> {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                return stackPane.getChildren().stream()
                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().equals(component.getName()));
            }
            return false;
        });
    }
    
    public void updateComponentVisualization(FarmComponent component, String oldName) {
       
        layoutPane.getChildren().removeIf(node -> {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                return stackPane.getChildren().stream()
                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().equals(oldName));
            }
            return false;
        });

        // Add the updated visualization
        Rectangle rectangle = new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);

        Label label = new Label(component.getName());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(component.getX());
        stackPane.setLayoutY(component.getY());
        stackPane.setPrefSize(component.getWidth(), component.getHeight());
        stackPane.getChildren().addAll(rectangle, label);

        layoutPane.getChildren().add(stackPane);
    }
    
    public void updateComponentDimensions(FarmComponent component) {
       
        layoutPane.getChildren().removeIf(node -> {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                return stackPane.getChildren().stream()
                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().equals(component.getName()));
            }
            return false;
        });

        Rectangle rectangle = new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);

        Label label = new Label(component.getName());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(component.getX());
        stackPane.setLayoutY(component.getY());
        stackPane.setPrefSize(component.getWidth(), component.getHeight());
        stackPane.getChildren().addAll(rectangle, label);

        layoutPane.getChildren().add(stackPane);
    }
    public void updateComponentLocation(FarmComponent component) {
       
        layoutPane.getChildren().removeIf(node -> {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                return stackPane.getChildren().stream()
                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().equals(component.getName()));
            }
            return false;
        });  
        Rectangle rectangle = new Rectangle(component.getWidth(), component.getHeight());
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);

        Label label = new Label(component.getName());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(component.getX()); 
        stackPane.setLayoutY(component.getY());
        stackPane.setPrefSize(component.getWidth(), component.getHeight());
        stackPane.getChildren().addAll(rectangle, label);

        layoutPane.getChildren().add(stackPane);
    }
    public void displayLayout() {
        rootContainer.display();
    }
}