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
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityImportMapper;
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
  private WeatherEntityImportMapper weatherEntityImportMapper;

  @InjectMocks
  private WeatherRepositoryImpl weatherRepository;

  @Test
  public void shouldSaveAndFlush() {
    Weather importedWeather = mock(Weather.class);
    WeatherEntity mappedWeather = mock(WeatherEntity.class);
    given(weatherEntityImportMapper.mapToWeatherEntity(importedWeather)).willReturn(mappedWeather);

    weatherRepository.saveAndFlush(importedWeather);

    then(weatherEntityImportMapper).should().mapToWeatherEntity(importedWeather);
    then(weatherJpaRepository).should().saveAndFlush(mappedWeather);
  }

}
