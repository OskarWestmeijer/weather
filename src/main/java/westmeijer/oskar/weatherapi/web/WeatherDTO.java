package westmeijer.oskar.weatherapi.web;

import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Weather representation used by the web layer.
 */
public class WeatherDTO {

    private final String zipCode;

    private final ZonedDateTime responseTime;

    private final List<Weather> weatherData;

    public WeatherDTO(String zipCode, ZonedDateTime responseTime, List<Weather> weatherData) {
        this.zipCode = zipCode;
        this.responseTime = responseTime.truncatedTo(ChronoUnit.SECONDS);
        this.weatherData = weatherData;
    }

    public String getZipCode() {
        return zipCode;
    }

    public ZonedDateTime getResponseTime() {
        return responseTime;
    }

    public List<Weather> getWeatherData() {
        return weatherData;
    }
}
