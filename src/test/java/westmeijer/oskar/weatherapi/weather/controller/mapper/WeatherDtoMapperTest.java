package westmeijer.oskar.weatherapi.weather.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.PagingDetails;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WeatherDtoMapperImpl.class,
})
public class WeatherDtoMapperTest {

  @Autowired
  private WeatherDtoMapper weatherDtoMapper;

  @Test
  void shouldMapToWeatherResponse() {
    Location location = TestLocationFactory.locationWithoutWeather();

    List<Weather> weatherList = List.of(
        new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d, Instant.now().truncatedTo(ChronoUnit.MICROS)),
        new Weather(UUID.randomUUID(), 5.00d, 30, 4.00d, Instant.now().truncatedTo(ChronoUnit.MICROS)));

    var pagingDetails = new PagingDetails(5, 5, false);

    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList, pagingDetails);

    then(weatherResponse.getCityName()).isEqualTo(location.cityName());
    then(weatherResponse.getCountry()).isEqualTo(location.country());
    then(weatherResponse.getLocationId()).isEqualTo(location.locationId());
    then(weatherResponse.getPagingDetails()).isEqualTo(pagingDetails);
  }

  @Test
  void shouldMapToPagingDetails() {
    var pageRecordsCount = 1;
    var totalRecordsCount = 10;
    var hasNewerRecords = true;
    var nextFrom = Instant.now();
    var nextLink = "nextLink";

    var actual = weatherDtoMapper.mapTo(pageRecordsCount, totalRecordsCount, hasNewerRecords, nextFrom, nextLink);
    then(actual)
        .hasFieldOrPropertyWithValue("pageRecordsCount", pageRecordsCount)
        .hasFieldOrPropertyWithValue("totalRecordsCount", totalRecordsCount)
        .hasFieldOrPropertyWithValue("hasNewerRecords", hasNewerRecords)
        .hasFieldOrPropertyWithValue("nextFrom", nextFrom)
        .hasFieldOrPropertyWithValue("nextLink", nextLink);
  }

  @Test
  void shouldMapToWeatherDto() {
    Weather weather = new Weather(UUID.randomUUID(), 12.00d, 45, 10.55d,
        Instant.now().truncatedTo(ChronoUnit.MICROS));

    WeatherDto weatherDTO = weatherDtoMapper.mapTo(weather);

    assertThat(weatherDTO)
        .returns(weather.humidity(), WeatherDto::getHumidity)
        .returns(weather.temperature(), WeatherDto::getTemperature)
        .returns(weather.windSpeed(), WeatherDto::getWindSpeed)
        .returns(weather.recordedAt(), WeatherDto::getRecordedAt);
  }

}
