public class Weather {
    private double humidity;
    private String forecast;

    // Default constructor
    public Weather() {
        this.humidity = 0.0;
        this.forecast = "Unknown";
    }

    // Method to check weather
    public void checkWeather() {
        // Example logic for checking the weather
        this.humidity = 75.0;  // Sample humidity
        this.forecast = "Clear";  // Sample forecast
    }

    // Getter for humidity
    public double getHumidity() {
        return humidity;
    }

    // Getter for forecast
    public String getForecast() {
        return forecast;
    }

    // Method to alert the farmer
    public void alertFarmer() {
        // Logic to alert farmer about weather
        System.out.println("Alert: Weather conditions - " + forecast + " with humidity " + humidity + "%.");
    }
}
