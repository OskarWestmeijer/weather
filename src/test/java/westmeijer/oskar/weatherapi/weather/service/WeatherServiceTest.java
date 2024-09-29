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
  public void shouldGetWeather() {
    var locationId = 10;
    var from = mock(Instant.class);
    var limit = 1000;
    Weather expectedWeather = mock(Weather.class);
    given(weatherRepository.getWeather(locationId, from, limit)).willReturn(List.of(expectedWeather));

    List<Weather> actualWeather = weatherService.getWeather(locationId, from, limit);
    then(actualWeather).isEqualTo(List.of(expectedWeather));

    BDDMockito.then(weatherRepository).should().getWeather(locationId, from, limit);
  }

  @ParameterizedTest
  @CsvSource({
      "null, '2023-09-25T10:00:00Z', 10, 'locationId is required'",
      "1, null, 10, 'from is required'",
      "1, '2023-09-25T10:00:00Z', null, 'limit is required'"
  })
  void getWeatherThrowsNpe(String locationIdStr, String fromStr, String limitStr, String expectedMessage) {
    var locationId = "null".equals(locationIdStr) ? null : Integer.valueOf(locationIdStr);
    var from = "null".equals(fromStr) ? null : Instant.parse(fromStr);
    var limit = "null".equals(limitStr) ? null : Integer.valueOf(limitStr);

    thenThrownBy(() -> weatherService.getWeather(locationId, from, limit))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining(expectedMessage);

    BDDMockito.then(weatherRepository).shouldHaveNoInteractions();
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
