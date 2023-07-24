package westmeijer.oskar.weatherapi.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.controller.model.WeatherDto;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;


public class WeatherDtoMapperTest {

  private final WeatherDtoMapper weatherDtoMapper = Mappers.getMapper(WeatherDtoMapper.class);

  @Test
  public void successfulMappingToResponse() {
    Location location = new Location("1234", "5678", "Luebeck", "Germany", Instant.now(), Instant.now());
    List<Weather> weatherList = List.of(
        new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d, "1234", Instant.now()),
        new Weather(UUID.randomUUID(), 5.00d, 30, 4.00d, "1234", Instant.now()));

    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);

    assertThat(weatherResponse.getCityName()).isEqualTo(location.cityName());
    assertThat(weatherResponse.getCountry()).isEqualTo(location.country());
    assertThat(weatherResponse.getLocalZipCode()).isEqualTo(String.valueOf(location.localZipCode()));
    assertThat(weatherResponse.getResponseTimestamp()).isNotNull();
    assertThat(weatherResponse.getTimeFormat()).isEqualTo("UTC");
  }

  @Test
  public void successfulMappingToWeatherDTO() {
    Weather weather = new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d, "1234", Instant.now().truncatedTo(ChronoUnit.MICROS));

    WeatherDto weatherDTO = weatherDtoMapper.mapTo(weather);

    assertThat(weatherDTO.id()).isEqualTo(weather.id());
    assertThat(weatherDTO.humidity()).isEqualTo(weather.humidity());
    assertThat(weatherDTO.recordedAt()).isEqualTo(weather.recordedAt());
    assertThat(weatherDTO.temperature()).isEqualTo(weather.temperature());
    assertThat(weatherDTO.windSpeed()).isEqualTo(weather.windSpeed());
  }

}
