package westmeijer.oskar.weatherapi.weather.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  public void shouldGetLast24h() {
    Weather expectedWeather = mock(Weather.class);
    Integer locationId = 1;
    given(weatherRepository.getLast24h(locationId)).willReturn(List.of(expectedWeather));

    List<Weather> actualWeather = weatherService.getLast24h(locationId);
    assertThat(actualWeather).isEqualTo(List.of(expectedWeather));

    then(weatherRepository).should().getLast24h(locationId);
  }

  @Test
  public void getLast24hThrowsNpe() {
    thenThrownBy(() -> weatherService.getLast24h(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(weatherRepository).shouldHaveNoInteractions();
  }

}
