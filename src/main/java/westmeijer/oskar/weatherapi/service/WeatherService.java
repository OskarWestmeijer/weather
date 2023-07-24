package westmeijer.oskar.weatherapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.service.model.Location;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {


  private final OpenWeatherApiClient openWeatherApiClient;

  private final WeatherJpaRepository weatherJpaRepository;


  /**
   * Retrieves weather of the last 24h from the database.
   *
   * @param localZipCode - get weather for this location
   * @return
   */
  public List<WeatherEntity> getLast24h(String localZipCode) {
    List<WeatherEntity> weatherEntityData = weatherJpaRepository.getLatestEntries(localZipCode);

    return weatherEntityData.stream()
        .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
        .toList();
  }

  /**
   * Retrieves weather of the last 3 days from the database.
   *
   * @param localZipCode - get weather for this location
   * @return
   */
  public List<WeatherEntity> getLast3Days(String localZipCode) {
    List<WeatherEntity> weatherEntityData = weatherJpaRepository.getLastThreeDays(localZipCode);

    return weatherEntityData.stream()
        .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
        .toList();
  }

  /**
   * Retrieve weather for the provided date.
   *
   * @param localZipCode
   * @param start        instant at start of day for zoneId
   * @return
   */
  public List<WeatherEntity> getSpecificDate(String localZipCode, Instant start) {
    Instant end = start.plus(1L, ChronoUnit.DAYS);
    log.debug("start instant: {}", start);
    log.debug("end instant: {}", end);

    List<WeatherEntity> weatherEntityData = weatherJpaRepository.getSpecificDay(localZipCode, start, end);

    return weatherEntityData.stream()
        .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
        .toList();
  }

  public WeatherEntity getNow(Location location) {
    return openWeatherApiClient.requestWeather(location);
  }

}
