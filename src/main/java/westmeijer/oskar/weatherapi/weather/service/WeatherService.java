package westmeijer.oskar.weatherapi.weather.service;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherRepository weatherRepository;

  /**
   * Retrieves weather of the last 24h from the database.
   *
   * @param localZipCode - get weather for this location
   * @return
   */
  public List<Weather> getLast24h(String localZipCode) {
    List<Weather> weatherList = weatherRepository.getLast24h(localZipCode);

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
    List<Weather> weatherList = weatherRepository.getLast3Days(localZipCode);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::recordedAt).reversed())
        .toList();
  }

  public Weather getLatestWeather(Location location){
    requireNonNull(location, "Location must not be null");
    return weatherRepository.getLatest(location);
  }

  public Weather saveAndFlush(Weather importedWeather) {
    requireNonNull(importedWeather, "importedWeather must not be null");
    return weatherRepository.saveAndFlush(importedWeather);
  }
}
