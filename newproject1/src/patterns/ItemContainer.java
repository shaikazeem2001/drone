package patterns;

public class ItemContainer extends FarmComponent {
    private String itemName;
    private int quantity;
    private double price;

    // Constructor
    public ItemContainer(String name, int quantity, double price, double width, double height) {
        super(name, "Container");  // Using "Container" as a type, can be changed
        this.itemName = name;
        this.quantity = quantity;
        this.price = price;
        setWidth(width);
        setHeight(height);
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
        return "ItemContainer{itemName='" + itemName + "', quantity=" + quantity + ", price=" + price + "} " + super.toString();
    }
}
