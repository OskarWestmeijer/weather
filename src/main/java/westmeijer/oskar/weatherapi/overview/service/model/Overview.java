package westmeijer.oskar.weatherapi.overview.service.model;

import java.time.Instant;

public record Overview(
    Integer locationId,
    String cityName,
    String countryCode,
    Double temperature,
    Integer humidity,
    Double windSpeed,
    Instant recordedAt) {

}
