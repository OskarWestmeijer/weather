package westmeijer.oskar.weatherapi.location.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.UUID;

public record Location(Integer id,
                       UUID uuid,
                       String localZipCode,
                       String openWeatherApiLocationCode,
                       String cityName,
                       @Deprecated
                       String country,
                       String countryCode,
                       Instant lastImportAt) {

  public Location {
    requireNonNull(id, "id cannot be null");
    requireNonNull(uuid, "uuid cannot be null");
    requireNonNull(localZipCode, "localZipCode cannot be null");
    requireNonNull(openWeatherApiLocationCode, "openWeatherApiLocationCode cannot be null");
    requireNonNull(cityName, "cityName cannot be null");
    requireNonNull(country, "country cannot be null");
    requireNonNull(countryCode, "countryCode cannot be null");
    requireNonNull(lastImportAt, "lastImportAt cannot be null");
  }

}
