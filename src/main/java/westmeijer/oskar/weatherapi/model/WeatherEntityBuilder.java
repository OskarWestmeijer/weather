package westmeijer.oskar.weatherapi.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class WeatherEntityBuilder {
    private UUID id;
    private double temperature;
    private LocalDateTime timestamp;

    public WeatherEntityBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public WeatherEntityBuilder setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherEntityBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public WeatherEntity createWeatherEntity() {
        return new WeatherEntity(id, temperature, timestamp);
    }
}
