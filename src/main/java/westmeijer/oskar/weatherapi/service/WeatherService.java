package westmeijer.oskar.weatherapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {


  private final OpenWeatherApiClient openWeatherApiClient;

  private final WeatherRepository weatherRepository;


  /**
   * Retrieves weather of the last 24h from the database.
   *
   * @param localZipCode - get weather for this location
   * @return
   */
  public List<Weather> getLast24h(String localZipCode) {
    List<Weather> weatherList = weatherRepository.getLatestEntries(localZipCode);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::recordedAt).reversed())
        .toList();
  }

  /**
   * Retrieves weather of the last 3 days from the database.
   *
   * @param localZipCode - get weather for this location
   * @return
   */
  public List<Weather> getLast3Days(String localZipCode) {
    List<Weather> weatherList = weatherRepository.getLastThreeDays(localZipCode);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::recordedAt).reversed())
        .toList();
  }

  /**
   * Retrieve weather for the provided date.
   *
   * @param localZipCode
   * @param start        instant at start of day for zoneId
   * @return
   */
  public List<Weather> getSpecificDate(String localZipCode, Instant start) {
    Instant end = start.plus(1L, ChronoUnit.DAYS);
    log.debug("start instant: {}", start);
    log.debug("end instant: {}", end);

    List<Weather> weatherList = weatherRepository.getSpecificDay(localZipCode, start, end);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::recordedAt).reversed())
        .toList();
  }

  public Weather getNow(Location location) {
    return openWeatherApiClient.requestWithGeneratedClient(location);
  }

  public Weather saveAndFlush(Weather importedWeather) {
    Objects.requireNonNull(importedWeather, "importedWeather must not be null");
    return weatherRepository.saveAndFlush(importedWeather);
  }
}
