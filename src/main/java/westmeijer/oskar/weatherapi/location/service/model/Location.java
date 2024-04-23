package westmeijer.oskar.weatherapi.location.service.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public record Location(

    Integer locationId,
    UUID uuid,
    String localZipCode,
    String openWeatherApiLocationCode,
    String cityName,
    String country,
    String countryCode,
    String latitude,
    String longitude,
    Instant lastImportAt,
    List<Weather> weather) {

  // TODO: add validations

}
