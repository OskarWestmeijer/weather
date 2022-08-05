package westmeijer.oskar.weatherapi.openweatherapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.base.Preconditions;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class OpenApiResponse {


    private double temperature;

    @JsonCreator
    public OpenApiResponse(@JsonProperty("main") Map<String, String> main) {
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
