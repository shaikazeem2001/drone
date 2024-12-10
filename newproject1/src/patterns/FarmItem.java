package patterns;

public class FarmItem extends FarmComponent {
    private String itemName;
    private int quantity;

    // Constructor
    public FarmItem(String itemName, int quantity) {
        super();  // Assuming FarmComponent has a default constructor
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FarmItem{itemName='" + itemName + "', quantity=" + quantity + "}";
    }
}
