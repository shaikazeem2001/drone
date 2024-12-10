package patterns;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer {
    private List<FarmItem> items;

    // Constructor for initializing the container with specific items
    public ItemContainer(String itemName, int quantity, int width, int height) {
        this.items = new ArrayList<>();
        FarmItem item = new FarmItem(itemName, quantity);
        // If width and height are relevant for the item, you can add logic here to use them
        // For example, you might want to create a FarmComponent that includes width and height
        addItem(item);  // Adding the item to the container
    }

    // Add an item to the container
    public void addItem(FarmItem item) {
        this.items.add(item);
    }

    // Get all items
    public List<FarmItem> getItems() {
        return items;
    }

    // Override toString method
    @Override
    public String toString() {
        return "ItemContainer{" + "items=" + items + "}";
    }
}
