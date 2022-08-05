package westmeijer.oskar.weatherapi.web.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Weather representation used by the web layer.
 */
public class WeatherDTO {

    private final UUID id;

    private final double temperature;

    private final LocalDateTime timestamp;

    protected WeatherDTO(UUID id, double temperature, LocalDateTime timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
