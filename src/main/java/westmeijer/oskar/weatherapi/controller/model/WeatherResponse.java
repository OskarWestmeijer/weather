package westmeijer.oskar.weatherapi.controller.model;

import lombok.Value;
import westmeijer.oskar.weatherapi.repository.model.Location;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Weather representation used by the web layer.
 */
@Value
public class WeatherResponse {

    String timeFormat = "UTC";

    String cityName;

    String localZipCode;

    String country;

    Instant responseTimestamp;

    List<WeatherDTO> weatherData;

    public WeatherResponse(Instant responseTime, Location location, List<WeatherDTO> weatherData) {
        this.cityName = location.getCityName();
        this.localZipCode = String.valueOf(location.getLocalZipCode());
        this.country = location.getCountry();
        this.responseTimestamp = responseTime.truncatedTo(ChronoUnit.SECONDS);
        this.weatherData = weatherData;
    }

}
