package westmeijer.oskar.weatherapi.weather.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;


public class WeatherDtoMapperTest {

  private final WeatherDtoMapper weatherDtoMapper = Mappers.getMapper(WeatherDtoMapper.class);

  @Test
  public void successfulMappingToResponse() {
    Location location = TestLocationFactory.location();

    List<Weather> weatherList = List.of(
        new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d, location, Instant.now().truncatedTo(ChronoUnit.MICROS)),
        new Weather(UUID.randomUUID(), 5.00d, 30, 4.00d, location, Instant.now().truncatedTo(ChronoUnit.MICROS)));

    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);

    assertThat(weatherResponse.getCityName()).isEqualTo(location.cityName());
    assertThat(weatherResponse.getCountry()).isEqualTo(location.country());
    assertThat(weatherResponse.getLocationId()).isEqualTo(location.locationId());
  }

  @Test
  public void successfulMappingToWeatherDTO() {
    Weather weather = new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d, TestLocationFactory.location(),
        Instant.now().truncatedTo(ChronoUnit.MICROS));

    WeatherDto weatherDTO = weatherDtoMapper.mapTo(weather);

    assertThat(weatherDTO)
        .returns(weather.humidity(), WeatherDto::getHumidity)
        .returns(weather.temperature(), WeatherDto::getTemperature)
        .returns(weather.windSpeed(), WeatherDto::getWindSpeed)
        .returns(weather.recordedAt(), WeatherDto::getRecordedAt);
  }

}
