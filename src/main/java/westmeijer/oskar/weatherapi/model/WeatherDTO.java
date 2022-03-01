package westmeijer.oskar.weatherapi.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Weather representation used by the web layer.
 */
public class WeatherDTO {

    private final UUID id;

    private final long temperature;

    private final LocalDateTime timestamp;

    protected WeatherDTO(UUID id, long temperature, LocalDateTime timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public long getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
