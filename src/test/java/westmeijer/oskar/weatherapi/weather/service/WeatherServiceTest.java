package westmeijer.oskar.weatherapi.weather.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

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
  public void shouldSaveWeather() {
    Weather importedWeather = mock(Weather.class);
    weatherService.saveAndFlush(importedWeather);
    then(weatherRepository).should().saveAndFlush(importedWeather);
  }

  @Test
  public void shouldNotSaveNullWeather() {
    assertThatThrownBy(() -> weatherService.saveAndFlush(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("importedWeather must not be null");

    then(weatherRepository).shouldHaveNoInteractions();
  }

}
