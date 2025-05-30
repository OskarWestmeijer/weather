package westmeijer.oskar.weatherapi.weather.repository;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.weather.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapper;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Component
@RequiredArgsConstructor
public class WeatherRepositoryImpl implements WeatherRepository {

  private final WeatherEntityMapper weatherEntityMapper;

  private final WeatherJpaRepository weatherJpaRepository;

  @Override
  public List<Weather> getWeather(Integer locationId, Instant from, Integer limit) {
    requireNonNull(locationId, "locationId is required");
    requireNonNull(from, "from is required");
    requireNonNull(limit, "limit is required");
    var weatherEntities = weatherJpaRepository.getWeather(locationId, from, limit);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public int getTotalCount(Integer locationId, Instant from) {
    requireNonNull(locationId, "locationId is required");
    requireNonNull(from, "from is required");
    return weatherJpaRepository.getTotalCount(locationId, from);
  }

}
