package westmeijer.oskar.weatherapi.controller.model;

import java.time.Instant;

public record LocationDto(String localZipCode,
                          String locationCode,
                          String cityName,
                          String country,
                          Instant lastImportAt) {

}
