package application;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import patterns.FarmComponent;
import patterns.FarmItem;
import patterns.ItemContainer;

public class TreeViewManager {
    private TreeView<String> treeView;
    private FarmLayoutManager layoutManager;
    private ItemContainer rootContainer;

    public TreeViewManager(TreeView<String> treeView, FarmLayoutManager layoutManager, ItemContainer rootContainer) {
        this.treeView = treeView;
        this.layoutManager = layoutManager;
        this.rootContainer = rootContainer;

        initializeTreeView();
    }

    private void initializeTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Root");
        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);
    }

    public void addItem(String name, double x, double y, double width, double height, double price, TreeItem<String> selectedNode) {
        FarmItem item = new FarmItem(name, x, y, width, height, price); // Pass the price
        if (selectedNode != null) {
            TreeItem<String> itemNode = new TreeItem<>(name);
            selectedNode.getChildren().add(itemNode);
            rootContainer.add(item);
        } else {
            throw new IllegalArgumentException("Error: Select a container to add the item.");
        }
        layoutManager.visualizeComponent(item);
    }


    public void addItemContainer(String name, double x, double y, double width, double height, TreeItem<String> selectedNode) {
        ItemContainer container = new ItemContainer(name, x, y, width, height);
        if (selectedNode != null) {
            TreeItem<String> containerNode = new TreeItem<>(name);
            selectedNode.getChildren().add(containerNode);
            rootContainer.add(container);
        } else {
            TreeItem<String> containerNode = new TreeItem<>(name);
            treeView.getRoot().getChildren().add(containerNode);
            rootContainer.add(container);
        }
        layoutManager.visualizeComponent(container);
    }


public void deleteItem(TreeItem<String> selectedNode) {
    if (selectedNode == null) {
        throw new IllegalArgumentException("Error: No item selected for deletion.");
    }
    String name = selectedNode.getValue();
    FarmComponent componentToDelete = findComponentByName(rootContainer, name);
    if (componentToDelete != null) {
        rootContainer.remove(componentToDelete);
        TreeItem<String> parentNode = selectedNode.getParent();
        if (parentNode != null) {
            parentNode.getChildren().remove(selectedNode);
        } else {
            treeView.getRoot().getChildren().remove(selectedNode);
        }
        layoutManager.removeComponentVisualization(componentToDelete);
    } else {
        throw new IllegalArgumentException("Error: Selected item not found in the data model.");
    }
}

public void deleteItemContainer(TreeItem<String> selectedNode) {
    if (selectedNode == null) {
        throw new IllegalArgumentException("Error: No container selected for deletion.");
    }
    String name = selectedNode.getValue();
    FarmComponent containerToDelete = findComponentByName(rootContainer, name);

    if (containerToDelete instanceof ItemContainer) {
        rootContainer.remove(containerToDelete);
        TreeItem<String> parentNode = selectedNode.getParent();
        if (parentNode != null) {
            parentNode.getChildren().remove(selectedNode);
        } else {
            treeView.getRoot().getChildren().remove(selectedNode);
        }
        layoutManager.removeComponentVisualization(containerToDelete);
    } else {
        throw new IllegalArgumentException("Error: Selected item is not a container.");
    }
}

public FarmComponent findComponentByName(FarmComponent current, String name) {
    if (current.getName().equals(name)) {
        return current;
    }
    if (current instanceof ItemContainer) {
        for (FarmComponent child : ((ItemContainer) current).getComponents()) {
            FarmComponent result = findComponentByName(child, name);
            if (result != null) {
                return result;
            }
        }
    }
    return null;
}
}
