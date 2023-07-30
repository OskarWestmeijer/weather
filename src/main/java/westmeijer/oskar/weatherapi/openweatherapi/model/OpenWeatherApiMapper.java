package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.google.common.base.Preconditions;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Component
public class OpenWeatherApiMapper {

  public Weather map(GeneratedOpenWeatherApiResponse response, String localZipCode) {
    Objects.requireNonNull(response);
    double temperature = response.getMain().getTemp();
    int humidity = response.getMain().getHumidity();
    double windSpeed = response.getWind().getSpeed();
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    return new Weather(UUID.randomUUID(), temperature, humidity, windSpeed, localZipCode, now);
  }


}
