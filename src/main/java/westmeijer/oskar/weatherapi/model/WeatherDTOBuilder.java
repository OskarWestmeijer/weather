package westmeijer.oskar.weatherapi.model;

import java.util.UUID;

public class WeatherDTOBuilder {
    private UUID id;
    private long temperature;

    public WeatherDTOBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public WeatherDTOBuilder setTemperature(long temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherDTO createWeatherDTO() {
        return new WeatherDTO(id, temperature);
    }
}
