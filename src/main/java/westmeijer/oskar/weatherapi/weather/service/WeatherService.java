package westmeijer.oskar.weatherapi.weather.service;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
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

  public List<Weather> getWeather(Integer locationId, Instant from, Integer limit) {
    requireNonNull(locationId, "locationId is required");
    requireNonNull(from, "from is required");
    requireNonNull(limit, "limit is required");
    return weatherRepository.getWeather(locationId, from, limit);
  }

  public int getTotalCount(Integer locationId, Instant from) {
    requireNonNull(locationId, "locationId is required");
    requireNonNull(from, "from is required");
    return weatherRepository.getTotalCount(locationId, from);
  }

  public List<Weather> getLast24h(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    List<Weather> weatherList = weatherRepository.getLast24h(locationId);

    return weatherList.stream()
        .sorted(Comparator.comparing(Weather::recordedAt).reversed())
        .toList();
  }

}
