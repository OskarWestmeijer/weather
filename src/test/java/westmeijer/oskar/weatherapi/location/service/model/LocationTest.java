package westmeijer.oskar.weatherapi.location.service.model;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class LocationTest {

  @Test
  void shouldThrowOnMissingLocationId() {
    thenThrownBy(
        () -> new Location(null, UUID.randomUUID(), "20535", "1",
            "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");
  }

  @Test
  void shouldThrowOnMissingUuid() {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, null, "20535", "1",
            "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("uuid is required");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = "abc")
  void shouldThrowOnNullOrEmptyLocalZipCode(String localZipCode) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), localZipCode, "1",
            "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("localZipCode is required and must be numeric [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = "abc")
  void shouldThrowOnNullOrEmptyLocationCode(String openWeatherApiLocationCode) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", openWeatherApiLocationCode,
            "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("openWeatherApiLocationCode is required and must be numeric [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyCityName(String cityName) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            cityName, "country", "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("cityName is required [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyCountry(String country) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            "cityName", country, "FIN", "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("country is required [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"fin", "FiN", "FI", "Finland"})
  void shouldThrowOnNullOrEmptyCountryCode(String countryCode) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            "cityName", "country", countryCode, "latitude", "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("countryCode is required (ISO 3166-1 alpha-3 code) [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyLatitude(String latitude) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            "cityName", "country", "FIN", latitude, "longitude", Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("latitude is required [%s]".formatted(locationId));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyLongitude(String longitude) {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            "cityName", "country", "FIN", "latitude", longitude, Instant.now(),
            Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("longitude is required [%s]".formatted(locationId));
  }

  @Test
  void shouldAllowNullableLastImportAt() {
    Integer locationId = 1;
    Location location = assertDoesNotThrow(() -> new Location(locationId, UUID.randomUUID(), "20535", "1",
        "cityName", "country", "FIN", "latitude", "longitude", null,
        Collections.emptyList()));
    then(location.lastImportAt()).isNull();
  }

  @Test
  void shouldThrowOnMissingWeather() {
    Integer locationId = 1;
    thenThrownBy(
        () -> new Location(locationId, UUID.randomUUID(), "20535", "1",
            "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
            null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("weather is required");
  }

}
