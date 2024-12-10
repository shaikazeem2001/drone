package patterns;

public class Weather {
    private double humidity;
    private String forecast;
    private double temperature; // Add temperature field

    // Default constructor
    public Weather() {
        this.humidity = 0.0;
        this.forecast = "Unknown";
        this.temperature = 0.0; // Initialize temperature
    }

    // Method to check weather
    public void checkWeather() {
        // Example logic for checking the weather
        this.humidity = 75.0;  // Sample humidity
        this.forecast = "Clear";  // Sample forecast
        this.temperature = 25.0; // Sample temperature
    }

    // Getter for humidity
    public double getHumidity() {
        return humidity;
    }

    // Getter for forecast
    public String getForecast() {
        return forecast;
    }

    // Getter for temperature (added method)
    public double getTemperature() {
        return temperature;
    }

    // Method to alert the farmer
    public void alertFarmer() {
        // Logic to alert farmer about weather
        System.out.println("Alert: Weather conditions - " + forecast + " with humidity " + humidity + "% and temperature " + temperature + "°C.");
    }
}
