package westmeijer.oskar.weatherapi.controller.model;

import lombok.Value;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

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

  List<WeatherDto> weatherData;

  public WeatherResponse(Instant responseTime, LocationEntity locationEntity, List<WeatherDto> weatherData) {
    this.cityName = locationEntity.getCityName();
    this.localZipCode = String.valueOf(locationEntity.getLocalZipCode());
    this.country = locationEntity.getCountry();
    this.responseTimestamp = responseTime.truncatedTo(ChronoUnit.SECONDS);
    this.weatherData = weatherData;
  }

}
