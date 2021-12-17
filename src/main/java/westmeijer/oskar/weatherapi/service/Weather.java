package westmeijer.oskar.weatherapi.service;

public class Weather {

    private int temperature;

    public Weather(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }
}
