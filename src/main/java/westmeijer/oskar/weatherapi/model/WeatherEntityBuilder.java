package westmeijer.oskar.weatherapi.model;

import java.util.UUID;

public class WeatherEntityBuilder {
    private UUID id;
    private Long temperature;

    public WeatherEntityBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public WeatherEntityBuilder setTemperature(Long temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherEntity createWeatherEntity() {
        return new WeatherEntity(id, temperature);
    }
}
