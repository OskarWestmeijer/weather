package westmeijer.oskar.weatherapi.weather.service;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.weather.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherRepository weatherRepository;

  public Weather saveAndFlush(Weather importedWeather) {
    requireNonNull(importedWeather, "importedWeather is required");
    return weatherRepository.saveAndFlush(importedWeather);
  }

  public List<Weather> getLast24h(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    List<Weather> weatherList = weatherRepository.getLast24h(locationId);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
        .toList();
  }

}
