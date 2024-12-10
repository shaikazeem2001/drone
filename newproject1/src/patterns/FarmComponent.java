package patterns;

public class FarmComponent {
    private String name;
    private String type;

    // Constructor
    public FarmComponent(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Override toString method
    @Override
    public String toString() {
        return "FarmComponent{name='" + name + "', type='" + type + "'}";
    }
}
