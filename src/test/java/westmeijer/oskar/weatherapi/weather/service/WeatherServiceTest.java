package westmeijer.oskar.weatherapi.weather.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.weather.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

  @Mock
  private WeatherRepository weatherRepository;

  @InjectMocks
  private WeatherService weatherService;

  @Test
  void shouldReturnWeatherFeedPageWithDefaultsAndPagingDetails() {
    // Given
    Integer locationId = 10;
    Instant from = null;
    Integer limit = null;

    Instant defaultFrom = Instant.EPOCH;
    int defaultLimit = 1000;

    Weather weather1 = mock(Weather.class);
    Weather weather2 = mock(Weather.class);

    given(weatherRepository.getWeather(locationId, defaultFrom, defaultLimit + 1))
        .willReturn(List.of(weather1, weather2));

    BDDMockito.willReturn(2)
        .given(weatherRepository).getTotalCount(locationId, defaultFrom);

    // When
    var result = weatherService.getWeatherFeedPage(locationId, from, limit);

    // Then
    then(result).isNotNull();
    then(result.weatherList()).hasSize(2);
    then(result.weatherList()).containsExactly(weather1, weather2);

    var paging = result.pagingDetails();
    then(paging).isNotNull();
    then(paging.totalRecords()).isEqualTo(2);
    then(paging.pageRecords()).isEqualTo(2);
    then(paging.hasNewerRecords()).isFalse();
    then(paging.nextLink()).contains("locationId=10");
    then(paging.nextLink()).contains("limit=" + defaultLimit);

    BDDMockito.then(weatherRepository).should().getWeather(locationId, defaultFrom, defaultLimit + 1);
    BDDMockito.then(weatherRepository).should().getTotalCount(locationId, defaultFrom);
  }

  @Test
  public void shouldGetTotalCount() {
    var locationId = 1;
    var from = mock(Instant.class);
    int expectedTotalCount = 5;
    given(weatherRepository.getTotalCount(locationId, from)).willReturn(expectedTotalCount);

    int actualTotalCount = weatherService.getTotalCount(locationId, from);
    then(actualTotalCount).isEqualTo(expectedTotalCount);

    BDDMockito.then(weatherRepository).should().getTotalCount(locationId, from);
  }

  @ParameterizedTest
  @CsvSource({
      "null, '2023-09-25T10:00:00Z', 'locationId is required'",
      "1, null, 'from is required'",
  })
  void getTotalCountThrowsNpe(String locationIdStr, String fromStr, String expectedMessage) {
    var locationId = "null".equals(locationIdStr) ? null : Integer.valueOf(locationIdStr);
    var from = "null".equals(fromStr) ? null : Instant.parse(fromStr);

    thenThrownBy(() -> weatherService.getTotalCount(locationId, from))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining(expectedMessage);

    BDDMockito.then(weatherRepository).shouldHaveNoInteractions();
  }

}
