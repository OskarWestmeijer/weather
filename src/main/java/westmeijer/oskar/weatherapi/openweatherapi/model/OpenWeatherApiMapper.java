package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.repository.model.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class OpenWeatherApiMapper {

  /**
   * Maps OpenWeatherApi Response to Entity object.
   */
  public static Weather map(OpenWeatherApiResponse response, String localZipCode) {
    Preconditions.checkNotNull(response);
    double temperature = response.main().temperature();
    int humidity = response.main().humidity();
    double windSpeed = response.wind().windSpeed();
    Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    return new Weather(UUID.randomUUID(), temperature, humidity, windSpeed, localZipCode, now, now);
  }


}
