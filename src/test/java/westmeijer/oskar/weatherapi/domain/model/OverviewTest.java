package westmeijer.oskar.weatherapi.domain.model;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class OverviewTest {

  @Test
  void shouldThrowOnLocationIdNull() {
    thenThrownBy(() -> new Overview(null, "cityName", "FIN", "53.8689", "10.6866", 1.55d, 12, 12.3d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyCityName(String cityName) {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, cityName, "FIN", "53.8689", "10.6866", 1.55d, 12, 12.3d, Instant.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("cityName is required [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"fin", "FiN", "FI", "Finland"})
  void shouldThrowOnNullOrEmptyCountryCode(String countryCode) {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", countryCode, "53.8689", "10.6866", 1.55d, 12, 12.3d, Instant.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("countryCode is required (ISO 3166-1 alpha-3 code) [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyLatitude(String latitude) {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", latitude, "10.6866", 1.55d, 12, 12.3d, Instant.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("latitude is required [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyLongitude(String longitude) {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", "53.8689", longitude, 1.55d, 12, 12.3d, Instant.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("longitude is required [%s]".formatted(locationId));
  }

  @Test
  void shouldThrowOnTemperatureNull() {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", "53.8689", "10.6866", null, 12, 12.3d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("temperature is required");
  }

  @Test
  void shouldThrowOnHumidityNull() {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", "53.8689", "10.6866", 12d, null, 12.3d, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("humidity is required");
  }

  @Test
  void shouldThrowOnWindSpeedNull() {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", "53.8689", "10.6866", 12d, 55, null, Instant.now()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("windSpeed is required");
  }

  @Test
  void shouldThrowOnRecordedAtNull() {
    Integer locationId = 1;
    thenThrownBy(() -> new Overview(locationId, "cityName", "FIN", "53.8689", "10.6866", 12d, 55, 23d, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("recordedAt is required");
  }

}
