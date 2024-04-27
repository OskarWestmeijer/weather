package westmeijer.oskar.weatherapi.weather.service.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Weather(
    UUID id,
    Double temperature,
    Integer humidity,
    Double windSpeed,
    Instant recordedAt) {

  public Weather {
    Objects.requireNonNull(id, "id is required");
    Objects.requireNonNull(temperature, "temperature is required");
    Objects.requireNonNull(humidity, "humidity is required");
    Objects.requireNonNull(windSpeed, "windSpeed is required");
    Objects.requireNonNull(recordedAt, "recordedAt is required");
  }

}
