package westmeijer.oskar.weatherapi.service.model;

import java.time.Instant;
import java.util.UUID;

public record Weather(UUID id,
                      Double temperature,
                      Integer humidity,
                      Double windSpeed,
                      String localZipCode,
                      Instant recordedAt) {

}
