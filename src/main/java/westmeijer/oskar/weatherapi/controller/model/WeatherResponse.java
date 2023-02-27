package westmeijer.oskar.weatherapi.controller.model;

import lombok.Value;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Weather representation used by the web layer.
 */
@Value
public class WeatherResponse {

    String timeFormat = "UTC";

    String location;

    String zipCode;

    String country;

    Instant responseTimestamp;

    List<Weather> weatherData;

    public WeatherResponse(Location location, Instant responseTime, List<Weather> weatherData) {
        this.location = location.getCityName();
        this.zipCode = String.valueOf(location.getZipCode());
        this.country = location.getCountry();
        this.responseTimestamp = responseTime.truncatedTo(ChronoUnit.SECONDS);
        this.weatherData = weatherData;
    }

}
