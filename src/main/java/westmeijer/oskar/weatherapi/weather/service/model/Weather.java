package westmeijer.oskar.weatherapi.weather.service.model;

import java.time.Instant;
import java.util.UUID;

public record Weather(
    UUID id,
    Double temperature,
    Integer humidity,
    Double windSpeed,
    Instant recordedAt) {

}
