package westmeijer.oskar.weatherapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public class TestWeatherFactory {

  public static Weather weather() {
    return new Weather(UUID.randomUUID(), 25.34d, 55, 10.34d, Instant.now().truncatedTo(ChronoUnit.MICROS));
  }

  public static WeatherEntity weatherEntityWithoutLocation() {
    return new WeatherEntity(UUID.randomUUID(), 22.54d, 34, 89.12d, null, Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS));
  }

}
