package patterns;

public class Weather {
    private String condition;
    private double temperature;

    public Weather(String condition, double temperature) {
        this.condition = condition;
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
