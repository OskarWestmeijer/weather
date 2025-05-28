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
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage;

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

    Weather weather1 = new Weather(
        UUID.randomUUID(), 12.00d, 45, 10.55d, Instant.now().truncatedTo(ChronoUnit.MICROS));
    Weather weather2 = new Weather(
        UUID.randomUUID(), 5.00d, 30, 4.00d, Instant.now().truncatedTo(ChronoUnit.MICROS));

    List<Weather> weatherList = List.of(weather1, weather2);
    String nextLink = "next";
    Instant nextFrom = Instant.now();
    var pagingDetails = WeatherFeedPage.PagingDetails.builder()
        .pageRecords(weatherList.size())
        .hasNewerRecords(false)
        .totalRecords(weatherList.size())
        .nextLink(nextLink)
        .nextFrom(nextFrom)
        .build();

    var weatherFeedPage = WeatherFeedPage.builder()
        .weatherList(weatherList)
        .pagingDetails(pagingDetails)
        .build();
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherFeedPage);

    then(weatherResponse).isNotNull();
    then(weatherResponse.getLocationId()).isEqualTo(location.locationId());
    then(weatherResponse.getCityName()).isEqualTo(location.cityName());
    then(weatherResponse.getCountry()).isEqualTo(location.country());

    then(weatherResponse.getWeatherData()).hasSize(2);

    then(weatherResponse.getWeatherData().get(0))
        .satisfies(data -> {
          then(data.getTemperature()).isEqualTo(weather1.temperature());
          then(data.getHumidity()).isEqualTo(weather1.humidity());
          then(data.getWindSpeed()).isEqualTo(weather1.windSpeed());
          then(data.getRecordedAt()).isEqualTo(weather1.recordedAt());
        });

    then(weatherResponse.getWeatherData().get(1))
        .satisfies(data -> {
          then(data.getTemperature()).isEqualTo(weather2.temperature());
          then(data.getHumidity()).isEqualTo(weather2.humidity());
          then(data.getWindSpeed()).isEqualTo(weather2.windSpeed());
          then(data.getRecordedAt()).isEqualTo(weather2.recordedAt());
        });

    then(weatherResponse.getPagingDetails()).satisfies(paging -> {
      then(paging.getPageRecordsCount()).isEqualTo(weatherList.size());
      then(paging.getTotalRecordsCount()).isEqualTo(weatherList.size());
      then(paging.getHasNewerRecords()).isFalse();
      then(paging.getNextLink()).isEqualTo(nextLink);
      then(paging.getNextFrom()).isEqualTo(nextFrom);
    });
  }

  @Test
  void shouldMapToPagingDetails() {
    var pageRecordsCount = 1;
    var totalRecordsCount = 10;
    var hasNewerRecords = true;
    var nextFrom = Instant.now();
    var nextLink = "nextLink";

    var pagingDetails = WeatherFeedPage.PagingDetails.builder()
        .pageRecords(pageRecordsCount)
        .hasNewerRecords(hasNewerRecords)
        .totalRecords(totalRecordsCount)
        .nextLink(nextLink)
        .nextFrom(nextFrom)
        .build();

    var actual = weatherDtoMapper.mapTo(pagingDetails);
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
