package westmeijer.oskar.weatherapi.weather.service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WeatherTest {

  @Test
  void shouldInitWeather() {
    UUID id = UUID.randomUUID();
    Double temp = 25.34d;
    Integer humidity = 55;
    Double windSpeed = 10.34d;
    Instant recordedAt = Instant.now();

    Weather weather = new Weather(id, temp, humidity, windSpeed, recordedAt);

    then(weather)
        .returns(id, Weather::id)
        .returns(temp, Weather::temperature)
        .returns(humidity, Weather::humidity)
        .returns(windSpeed, Weather::windSpeed)
        .returns(recordedAt, Weather::recordedAt);
  }

  @Test
  void shouldThrowOnIdNull() {
    thenThrownBy(() -> new Weather(null, 12d, 55, 6.44d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("id is required");
  }

  @Test
  void shouldThrowOnTemperatureNull() {
    thenThrownBy(() -> new Weather(UUID.randomUUID(), null, 55, 6.44d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("temperature is required");
  }

  @Test
  void shouldThrowOnHumidityNull() {
    thenThrownBy(() -> new Weather(UUID.randomUUID(), 12d, null, 6.44d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("humidity is required");
  }

  @Test
  void shouldThrowOnWindSpeedNull() {
    thenThrownBy(() -> new Weather(UUID.randomUUID(), 12d, 55, null, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("windSpeed is required");
  }

  @Test
  void shouldThrowOnRecordedAtNull() {
    thenThrownBy(() -> new Weather(UUID.randomUUID(), 12d, 55, 6.44d, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("recordedAt is required");
  }

}
