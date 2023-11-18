package westmeijer.oskar.weatherapi.weather.service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public class WeatherTest {

  @Test
  public void shouldInitWeather() {
    UUID id = UUID.randomUUID();
    Double temp = 25.34d;
    Integer humidity = 55;
    Double windSpeed = 10.34d;
    Instant recordedAt = Instant.now();

    Location location = TestLocationFactory.locationWithoutWeather();
    Weather weather = new Weather(id, temp, humidity, windSpeed, location, recordedAt);
    location.addWeather(weather);

    assertThat(weather)
        .returns(id, Weather::getId)
        .returns(temp, Weather::getTemperature)
        .returns(humidity, Weather::getHumidity)
        .returns(windSpeed, Weather::getWindSpeed)
        .returns(location, Weather::getLocation)
        .returns(recordedAt, Weather::getRecordedAt);
  }

}
