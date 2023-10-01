package westmeijer.oskar.weatherapi.weather.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public class WeatherEntityMapperTest {

  private final WeatherEntityMapper weatherEntityMapper = Mappers.getMapper(WeatherEntityMapper.class);

  @Test
  public void mapsToEntity() {
    Weather weather = new Weather(UUID.randomUUID(), 22.54d, 34, 89.12d, "1234", 1, Instant.now().truncatedTo(ChronoUnit.MICROS));

    WeatherEntity weatherEntity = weatherEntityMapper.map(weather);

    assertThat(weatherEntity.getId()).isEqualTo(weather.id());
    assertThat(weatherEntity.getHumidity()).isEqualTo(weather.humidity());
    assertThat(weatherEntity.getWindSpeed()).isEqualTo(weather.windSpeed());
    assertThat(weatherEntity.getModifiedAt()).isCloseTo(Instant.now().truncatedTo(ChronoUnit.MICROS), within(1, ChronoUnit.SECONDS));
    assertThat(weatherEntity.getRecordedAt()).isEqualTo(weather.recordedAt());
    assertThat(weatherEntity.getTemperature()).isEqualTo(weather.temperature());
    assertThat(weatherEntity.getLocalZipCode()).isEqualTo(weather.localZipCode());
  }

  @Test
  public void mapsToWeather() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    WeatherEntity weatherEntity = new WeatherEntity(UUID.randomUUID(), 22.54d, 34, 89.12d, "1234", 1, now, now);

    Weather weather = weatherEntityMapper.map(weatherEntity);

    assertThat(weatherEntity.getId()).isEqualTo(weather.id());
    assertThat(weatherEntity.getHumidity()).isEqualTo(weather.humidity());
    assertThat(weatherEntity.getWindSpeed()).isEqualTo(weather.windSpeed());
    assertThat(weatherEntity.getRecordedAt()).isEqualTo(weather.recordedAt());
    assertThat(weatherEntity.getTemperature()).isEqualTo(weather.temperature());
    assertThat(weatherEntity.getLocalZipCode()).isEqualTo(weather.localZipCode());
  }

  @Test
  public void mapsToWeatherList() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    WeatherEntity luebeck = new WeatherEntity(UUID.randomUUID(), 22.54d, 34, 89.12d, "1234", 1, now, now);
    WeatherEntity hamburg = new WeatherEntity(UUID.randomUUID(), 22.54d, 34, 89.12d, "1234", 1, now, now);

    List<Weather> weatherList = weatherEntityMapper.mapList(List.of(luebeck, hamburg));

    assertThat(weatherList.size()).isEqualTo(2);
    assertThat(weatherList).extracting("id", "temperature", "humidity", "windSpeed", "localZipCode", "recordedAt")
        .containsOnlyOnce(
            Tuple.tuple(luebeck.getId(), luebeck.getTemperature(), luebeck.getHumidity(), luebeck.getWindSpeed(), luebeck.getLocalZipCode(),
                luebeck.getRecordedAt()))
        .containsOnlyOnce(
            Tuple.tuple(hamburg.getId(), hamburg.getTemperature(), hamburg.getHumidity(), hamburg.getWindSpeed(), hamburg.getLocalZipCode(),
                hamburg.getRecordedAt()));
  }

}
