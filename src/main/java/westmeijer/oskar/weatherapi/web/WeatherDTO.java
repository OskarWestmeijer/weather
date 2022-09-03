package westmeijer.oskar.weatherapi.web;

import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Weather representation used by the web layer.
 */
public class WeatherDTO {

    private final String timeFormat = "UTC";

    private final String location;

    private final String zipCode;

    private final String country;

    private final Instant responseTimestamp;

    private final List<Weather> weatherData;

    public WeatherDTO(Location location, Instant responseTime, List<Weather> weatherData) {
        this.location = location.getCityName();
        this.zipCode = String.valueOf(location.getZipCode());
        this.country = location.getCountry();
        this.responseTimestamp = responseTime.truncatedTo(ChronoUnit.SECONDS);
        this.weatherData = weatherData;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Instant getResponseTimestamp() {
        return responseTimestamp;
    }

    public List<Weather> getWeatherData() {
        return weatherData;
    }

    public String getLocation() {
        return location;
    }
}
