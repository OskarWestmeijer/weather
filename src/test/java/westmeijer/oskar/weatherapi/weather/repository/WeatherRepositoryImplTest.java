package westmeijer.oskar.weatherapi.weather.repository;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

  @InjectMocks
  private WeatherRepositoryImpl weatherRepository;

  @Test
  public void saveAndFlushWeather() {
    Weather importedWeather = mock(Weather.class);
    WeatherEntity mappedWeather = mock(WeatherEntity.class);
    given(weatherEntityMapper.map(importedWeather)).willReturn(mappedWeather);

    weatherRepository.saveAndFlush(importedWeather);

    then(weatherEntityMapper).should().map(importedWeather);
    then(weatherJpaRepository).should().saveAndFlush(mappedWeather);
  }

}
