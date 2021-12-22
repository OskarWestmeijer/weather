package westmeijer.oskar.weatherapi.model;

import java.util.UUID;

/**
 * Weather representation used by the web layer.
 */
public class WeatherDTO {

    private final UUID id;

    private final long temperature;

    protected WeatherDTO(UUID id, long temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    public UUID getId() {
        return id;
    }

    public long getTemperature() {
        return temperature;
    }
}
