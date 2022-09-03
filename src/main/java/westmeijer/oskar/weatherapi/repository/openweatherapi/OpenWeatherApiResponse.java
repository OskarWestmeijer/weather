package westmeijer.oskar.weatherapi.repository.openweatherapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import java.util.Map;

public class OpenWeatherApiResponse {


    private double temperature;

    @JsonCreator
    public OpenWeatherApiResponse(@JsonProperty("main") Map<String, String> main) {
        Preconditions.checkNotNull(main);
        this.temperature = Double.parseDouble(main.get("temp"));
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "OpenApiResponse{" +
                "temperature=" + temperature +
                '}';
    }
}
