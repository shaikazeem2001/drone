package patterns;

public class FarmItem extends FarmComponent {
    private String itemName;
    private int quantity;
    private double price;

    // Constructor with the required parameters
    public FarmItem(String name, String type, String itemName, int quantity, double price) {
        super(name, type);  // Call to parent class constructor
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FarmItem{itemName='" + itemName + "', quantity=" + quantity + ", price=" + price + "} " + super.toString();
    }
}
