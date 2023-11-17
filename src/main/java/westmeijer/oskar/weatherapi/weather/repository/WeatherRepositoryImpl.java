package westmeijer.oskar.weatherapi.weather.repository;

import static java.util.Objects.requireNonNull;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapper;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Component
@RequiredArgsConstructor
public class WeatherRepositoryImpl implements WeatherRepository {

  private final WeatherEntityMapper weatherEntityMapper;

  private final WeatherJpaRepository weatherJpaRepository;

  @Override
  public List<Weather> getLast24h(Integer locationId) {
    requireNonNull(locationId, "locationId should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getLast24h(locationId);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public Weather saveAndFlush(Weather weather) {
    requireNonNull(weather, "weather must not be null");
    WeatherEntity weatherEntity = weatherEntityMapper.map(weather);
    WeatherEntity savedWeather = weatherJpaRepository.saveAndFlush(weatherEntity);
    return weatherEntityMapper.map(savedWeather);
  }

  @Override
  public Weather getLatest(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    WeatherEntity weatherEntity = weatherJpaRepository.getLatest(locationId);
    return weatherEntityMapper.map(weatherEntity);
  }


}
