package westmeijer.oskar.weatherapi.weather.repository;

import static java.util.Objects.requireNonNull;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<Weather> getLast24h(String localZipCode) {
    requireNonNull(localZipCode, "localZipCode should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getLast24h(localZipCode);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public List<Weather> getLast3Days(String localZipCode) {
    requireNonNull(localZipCode, "localZipCode should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getLast3Days(localZipCode);
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
  public Weather getLatest(Location location) {
    requireNonNull(location, "location must not be null");
    WeatherEntity weatherEntity = weatherJpaRepository.getLatest(location.id());
    return weatherEntityMapper.map(weatherEntity);
  }


}
