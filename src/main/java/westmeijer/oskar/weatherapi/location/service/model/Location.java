package westmeijer.oskar.weatherapi.location.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import javax.annotation.Nonnull;

public record Location(@Nonnull Integer id,
                       @Nonnull String localZipCode,
                       @Nonnull String openWeatherApiLocationCode,
                       @Nonnull String cityName,
                       @Nonnull String country,
                       @Nonnull Instant lastImportAt) {

  public Location {
    requireNonNull(id, "id cannot be null");
    requireNonNull(localZipCode, "localZipCode cannot be null");
    requireNonNull(openWeatherApiLocationCode, "openWeatherApiLocationCode cannot be null");
    requireNonNull(cityName, "cityName cannot be null");
    requireNonNull(country, "country cannot be null");
    requireNonNull(lastImportAt, "lastImportAt cannot be null");
  }

}
