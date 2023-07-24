package westmeijer.oskar.weatherapi.controller.model;

import lombok.Value;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import westmeijer.oskar.weatherapi.service.model.Location;

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

  List<WeatherDto> weatherData;

  public WeatherResponse(Instant responseTime, Location location, List<WeatherDto> weatherData) {
    this.cityName = location.cityName();
    this.localZipCode = String.valueOf(location.localZipCode());
    this.country = location.country();
    this.responseTimestamp = responseTime.truncatedTo(ChronoUnit.SECONDS);
    this.weatherData = weatherData;
  }

}
