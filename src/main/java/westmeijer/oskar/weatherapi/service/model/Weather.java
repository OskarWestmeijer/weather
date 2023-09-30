package westmeijer.oskar.weatherapi.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record Weather(@Nonnull UUID id,
                      @Nonnull Double temperature,
                      @Nonnull Integer humidity,
                      @Nonnull Double windSpeed,
                      @Nonnull String localZipCode,
                      @Nullable Integer locationId,
                      @Nonnull Instant recordedAt) {

  public Weather {
    requireNonNull(id, "id must not be null");
    requireNonNull(temperature, "temperature must not be null");
    requireNonNull(humidity, "humidity must not be null");
    requireNonNull(windSpeed, "windSpeed must not be null");
    requireNonNull(localZipCode, "localZipCode must not be null");
    requireNonNull(recordedAt, "recordedAt must not be null");
  }

}
