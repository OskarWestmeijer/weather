package westmeijer.oskar.weatherapi.weather.repository;

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
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityImportMapper;
import westmeijer.oskar.weatherapi.weather.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapper;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class WeatherRepositoryImplTest {

  @Mock
  private WeatherJpaRepository weatherJpaRepository;

  @Mock
  private WeatherEntityMapper weatherEntityMapper;

  @Mock
  private LocationEntityImportMapper weatherEntityImportMapper;

  @InjectMocks
  private WeatherRepositoryImpl weatherRepository;

  @Test
  void shouldGetWeather() {
    var locationId = 1;
    var from = mock(Instant.class);
    var limit = 5;

    WeatherEntity weatherEntity = mock(WeatherEntity.class);
    given(weatherJpaRepository.getWeather(locationId, from, limit)).willReturn(List.of(weatherEntity));

    Weather expectedWeather = mock(Weather.class);
    given(weatherEntityMapper.mapList(List.of(weatherEntity))).willReturn(List.of(expectedWeather));

    List<Weather> actualWeather = weatherRepository.getWeather(locationId, from, limit);

    then(actualWeather).isEqualTo(List.of(expectedWeather));

    BDDMockito.then(weatherJpaRepository).should().getWeather(locationId, from, limit);
    BDDMockito.then(weatherEntityMapper).should().mapList(List.of(weatherEntity));
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

    thenThrownBy(() -> weatherRepository.getWeather(locationId, from, limit))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining(expectedMessage);

    BDDMockito.then(weatherJpaRepository).shouldHaveNoInteractions();
    BDDMockito.then(weatherEntityMapper).shouldHaveNoInteractions();
  }

  @Test
  void shouldGetTotalCount() {
    var locationId = 1;
    var from = mock(Instant.class);
    var expectedTotalCount = 5;
    given(weatherJpaRepository.getTotalCount(locationId, from)).willReturn(expectedTotalCount);

    var actualTotalCount = weatherRepository.getTotalCount(locationId, from);
    then(actualTotalCount).isEqualTo(expectedTotalCount);

    BDDMockito.then(weatherJpaRepository).should().getTotalCount(locationId, from);
  }

  @ParameterizedTest
  @CsvSource({
      "null, '2023-09-25T10:00:00Z', 'locationId is required'",
      "1, null, 'from is required'",
  })
  void getTotalCountThrowsNpe(String locationIdStr, String fromStr, String expectedMessage) {
    var locationId = "null".equals(locationIdStr) ? null : Integer.valueOf(locationIdStr);
    var from = "null".equals(fromStr) ? null : Instant.parse(fromStr);

    thenThrownBy(() -> weatherRepository.getTotalCount(locationId, from))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining(expectedMessage);

    BDDMockito.then(weatherJpaRepository).shouldHaveNoInteractions();
  }

}
