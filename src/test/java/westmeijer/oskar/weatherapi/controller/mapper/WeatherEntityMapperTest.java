package westmeijer.oskar.weatherapi.controller.mapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.controller.model.WeatherDto;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.service.model.Location;


public class WeatherEntityMapperTest {

  private final WeatherDtoMapper weatherDtoMapper = Mappers.getMapper(WeatherDtoMapper.class);

  @Test
  public void successfulMappingToResponse() {
    Location location = new Location("1234", "5678", "Luebeck", "Germany", Instant.now(), Instant.now());
    List<WeatherEntity> weatherEntityList = List.of(
        new WeatherEntity(UUID.randomUUID(), 12.00d, 45, 10.55d, "1234", Instant.now(), Instant.now()),
        new WeatherEntity(UUID.randomUUID(), 5.00d, 30, 4.00d, "1234", Instant.now(), Instant.now()));

    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherEntityList);

    Assertions.assertThat(weatherResponse.getCityName()).isEqualTo(location.cityName());
    Assertions.assertThat(weatherResponse.getCountry()).isEqualTo(location.country());
    Assertions.assertThat(weatherResponse.getLocalZipCode()).isEqualTo(String.valueOf(location.localZipCode()));
    Assertions.assertThat(weatherResponse.getResponseTimestamp()).isNotNull();
    Assertions.assertThat(weatherResponse.getTimeFormat()).isEqualTo("UTC");
  }

  @Test
  public void successfulMappingToWeatherDTO() {
    WeatherEntity weatherEntity = new WeatherEntity(UUID.randomUUID(), 12.00d, 45, 10.55d, "1234", Instant.now(), Instant.now());

    WeatherDto weatherDTO = weatherDtoMapper.mapTo(weatherEntity);

    Assertions.assertThat(weatherDTO.id()).isEqualTo(weatherEntity.getId());
    Assertions.assertThat(weatherDTO.humidity()).isEqualTo(weatherEntity.getHumidity());
    Assertions.assertThat(weatherDTO.recordedAt()).isEqualTo(weatherEntity.getRecordedAt().truncatedTo(ChronoUnit.SECONDS));
    Assertions.assertThat(weatherDTO.temperature()).isEqualTo(weatherEntity.getTemperature());
    Assertions.assertThat(weatherDTO.windSpeed()).isEqualTo(weatherEntity.getWindSpeed());
  }

}
