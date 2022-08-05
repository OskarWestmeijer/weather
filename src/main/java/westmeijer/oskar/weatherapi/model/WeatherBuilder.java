package westmeijer.oskar.weatherapi.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class WeatherBuilder {
    private UUID id;
    private double temperature;
    private LocalDateTime timestamp;

    public WeatherBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public WeatherBuilder setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Weather createWeatherEntity() {
        return new Weather(id, temperature, timestamp);
    }
}
