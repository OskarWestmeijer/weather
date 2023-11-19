package westmeijer.oskar.weatherapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public class TestWeatherFactory {

  public static Weather weather() {
    Location location = new Location(1,
        UUID.randomUUID(),
        "1234",
        "5678",
        "Luebeck",
        "Germany",
        "GER",
        "51.659088",
        "6.966170",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        new ArrayList<>());
    Weather weather = new Weather(UUID.randomUUID(), 25.34d, 55, 10.34d, location, Instant.now().truncatedTo(ChronoUnit.MICROS));
    location.addWeather(weather);

    return weather;
  }

  public static WeatherEntity weatherEntityWithoutLocation() {
    return new WeatherEntity(UUID.randomUUID(), 22.54d, 34, 89.12d, null, Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS));
  }

}
