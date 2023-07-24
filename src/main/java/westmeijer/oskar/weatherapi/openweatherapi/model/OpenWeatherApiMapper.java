package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.google.common.base.Preconditions;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import westmeijer.oskar.weatherapi.service.model.Weather;

public class OpenWeatherApiMapper {

  public static Weather map(OpenWeatherApiResponse response, String localZipCode) {
    Preconditions.checkNotNull(response);
    double temperature = response.main().temperature();
    int humidity = response.main().humidity();
    double windSpeed = response.wind().windSpeed();
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    return new Weather(UUID.randomUUID(), temperature, humidity, windSpeed, localZipCode, now);
  }


}
