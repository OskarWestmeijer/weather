package westmeijer.oskar.weatherapi.location.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public record Location(Integer locationId,
                       UUID uuid,
                       String localZipCode,
                       String openWeatherApiLocationCode,
                       String cityName,
                       @Deprecated
                       String country,
                       String countryCode,
                       String latitude,
                       String longitude,
                       Instant lastImportAt,
                       List<Weather> weather) {

  public Location {
    requireNonNull(locationId, "locationId cannot be null");
    requireNonNull(uuid, "uuid cannot be null");
    requireNonNull(localZipCode, "localZipCode cannot be null");
    requireNonNull(openWeatherApiLocationCode, "openWeatherApiLocationCode cannot be null");
    requireNonNull(cityName, "cityName cannot be null");
    requireNonNull(country, "country cannot be null");
    requireNonNull(countryCode, "countryCode cannot be null");
    requireNonNull(latitude, "latitude is required");
    requireNonNull(longitude, "longitude is required");
    requireNonNull(lastImportAt, "lastImportAt cannot be null");
    requireNonNull(weather, "weather is required");
  }

}
