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
    Location location = TestLocationFactory.location();

    Weather weather = new Weather(id, temp, humidity, windSpeed, location, recordedAt);

    assertThat(weather)
        .returns(id, Weather::id)
        .returns(temp, Weather::temperature)
        .returns(humidity, Weather::humidity)
        .returns(windSpeed, Weather::windSpeed)
        .returns(location, Weather::location)
        .returns(recordedAt, Weather::recordedAt);
  }

  @Test
  public void shouldNotInitNull() {
    assertThatThrownBy(() -> new Weather(null, null, null, null, null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("id is required");
  }

}
