package westmeijer.oskar.weatherapi.service.model;

import java.time.Instant;

public record Location(String localZipCode,
                       String locationCode,
                       String cityName,
                       String country,
                       Instant modifiedAt,
                       Instant lastImportAt) {

}
