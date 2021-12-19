package westmeijer.oskar.weatherapi.service;

import java.util.UUID;

public class Weather {

    private final UUID id;

    private final long temperature;

    public Weather(UUID id, long temperature) {
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
