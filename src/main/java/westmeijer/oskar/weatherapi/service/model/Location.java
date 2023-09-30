package westmeijer.oskar.weatherapi.service.model;

import java.time.Instant;

public record Location(Integer id,
                       String localZipCode,
                       String openWeatherApiLocationCode,
                       String cityName,
                       String country,
                       Instant lastImportAt) {

}
