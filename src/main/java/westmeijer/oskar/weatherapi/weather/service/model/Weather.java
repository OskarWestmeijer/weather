package westmeijer.oskar.weatherapi.weather.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.UUID;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public record Weather(UUID id,
                      Double temperature,
                      Integer humidity,
                      Double windSpeed,
                      Location location,
                      Instant recordedAt) {

  public Weather {
    requireNonNull(id, "id is required");
    requireNonNull(temperature, "temperature is required");
    requireNonNull(humidity, "humidity is required");
    requireNonNull(windSpeed, "windSpeed is required");
    requireNonNull(location, "location is required");
    requireNonNull(recordedAt, "recordedAt is required");
  }

}
