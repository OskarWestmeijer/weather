package westmeijer.oskar.weatherapi.location.service.model;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public class LocationTest {

  private static final Logger log = LoggerFactory.getLogger(LocationTest.class);

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

  @Test
  void shouldMapWeatherListToUnmodifiableList() {
    List<Weather> mutableWeatherList = new ArrayList<>();
    mutableWeatherList.add(mock(Weather.class));

    var location = new Location(1, UUID.randomUUID(), "20535", "1",
        "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
        mutableWeatherList);

    thenThrownBy(() -> location.weather().add(mock(Weather.class)))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void shouldMakeDefensiveCopyOfWeatherList() {
    List<Weather> mutableWeatherList = new ArrayList<>();
    var weather1 = mock(Weather.class);
    mutableWeatherList.add(weather1);

    var location = new Location(1, UUID.randomUUID(), "20535", "1",
        "cityName", "country", "FIN", "latitude", "longitude", Instant.now(),
        mutableWeatherList);

    var weather2 = mock(Weather.class);
    mutableWeatherList.add(weather2);

    then(mutableWeatherList).hasSize(2).containsExactly(weather1, weather2);
    then(location.weather()).hasSize(1).containsExactly(weather1);
  }

}
