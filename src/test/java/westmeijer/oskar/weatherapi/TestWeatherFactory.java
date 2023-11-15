package westmeijer.oskar.weatherapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;
import westmeijer.oskar.weatherapi.location.service.model.Location;
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
        Collections.emptyList());
    Weather weather = new Weather(UUID.randomUUID(), 25.34d, 55, 10.34d, location, Instant.now().truncatedTo(ChronoUnit.MICROS));

    return weather;
  }


}
