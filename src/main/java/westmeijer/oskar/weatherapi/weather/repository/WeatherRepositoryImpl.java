package westmeijer.oskar.weatherapi.weather.repository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
  public List<Weather> getLatestEntries(String localZipCode) {
    Objects.requireNonNull(localZipCode, "localZipCode should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getLatestEntries(localZipCode);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public List<Weather> getLastThreeDays(String localZipCode) {
    Objects.requireNonNull(localZipCode, "localZipCode should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getLastThreeDays(localZipCode);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public List<Weather> getSpecificDay(String localZipCode, Instant start, Instant end) {
    Objects.requireNonNull(localZipCode, "localZipCode should not be null");
    Objects.requireNonNull(start, "start should not be null");
    Objects.requireNonNull(end, "end should not be null");
    List<WeatherEntity> weatherEntities = weatherJpaRepository.getSpecificDay(localZipCode, start, end);
    return weatherEntityMapper.mapList(weatherEntities);
  }

  @Override
  public Weather saveAndFlush(Weather weather) {
    Objects.requireNonNull(weather, "weather must not be null");
    WeatherEntity weatherEntity = weatherEntityMapper.map(weather);
    WeatherEntity savedWeather = weatherJpaRepository.saveAndFlush(weatherEntity);
    return weatherEntityMapper.map(savedWeather);
  }


}
