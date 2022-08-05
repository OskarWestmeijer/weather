package westmeijer.oskar.weatherapi.web.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class WeatherDTOBuilder {
    private UUID id;
    private double temperature;
    private LocalDateTime timestamp;

    public WeatherDTOBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public WeatherDTOBuilder setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherDTOBuilder setTimestmap(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public WeatherDTO createWeatherDTO() {
        return new WeatherDTO(id, temperature, timestamp);
    }
}
