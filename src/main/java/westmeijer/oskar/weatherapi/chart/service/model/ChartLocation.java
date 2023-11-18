package westmeijer.oskar.weatherapi.chart.service.model;

import java.time.Instant;
import java.util.UUID;

public record ChartLocation(
    Integer locationId,
    String cityName,
    String countryCode,
    Double temperature,
    Integer humidity,
    Double windSpeed,
    Instant recordedAt) {

}
