package westmeijer.oskar.weatherapi.service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class WeatherTest {

  @Test
  public void shouldInitWeather() {
    UUID id = UUID.randomUUID();
    Double temp = 25.34d;
    Integer humidity = 55;
    Double windSpeed = 10.34d;
    String localZipCode = "20535";
    Instant recordedAt = Instant.now();

    Weather weather = new Weather(id, temp, humidity, windSpeed, localZipCode, recordedAt);

    assertThat(weather)
        .returns(id, Weather::id)
        .returns(temp, Weather::temperature)
        .returns(humidity, Weather::humidity)
        .returns(windSpeed, Weather::windSpeed)
        .returns(localZipCode, Weather::localZipCode)
        .returns(recordedAt, Weather::recordedAt);
  }

  @Test
  public void shouldNotInitNull() {
    assertThatThrownBy(() -> new Weather(null, null, null, null, null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("id must not be null");
  }

}
