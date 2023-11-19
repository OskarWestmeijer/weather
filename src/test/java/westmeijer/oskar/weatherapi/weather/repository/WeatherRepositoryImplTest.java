package westmeijer.oskar.weatherapi.weather.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
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
  public void shouldGetLast24h() {
    Integer locationId = 1;
    WeatherEntity weatherEntity = mock(WeatherEntity.class);
    given(weatherJpaRepository.getLast24h(locationId)).willReturn(List.of(weatherEntity));

    Weather expectedWeather = mock(Weather.class);
    given(weatherEntityMapper.mapList(List.of(weatherEntity))).willReturn(List.of(expectedWeather));

    List<Weather> actualWeather = weatherRepository.getLast24h(locationId);

    assertThat(actualWeather).isEqualTo(List.of(expectedWeather));

    then(weatherJpaRepository).should().getLast24h(locationId);
    then(weatherEntityMapper).should().mapList(List.of(weatherEntity));
  }

  @Test
  public void getLast24hThrowsNpe() {
    assertThatThrownBy(() -> weatherRepository.getLast24h(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(weatherJpaRepository).shouldHaveNoInteractions();
    then(weatherEntityMapper).shouldHaveNoInteractions();
  }

  @Test
  public void shouldSaveAndFlush() {
    Weather importedWeather = mock(Weather.class);
    WeatherEntity mappedWeather = mock(WeatherEntity.class);
    given(weatherEntityImportMapper.mapToWeatherEntity(importedWeather)).willReturn(mappedWeather);

    weatherRepository.saveAndFlush(importedWeather);

    then(weatherEntityImportMapper).should().mapToWeatherEntity(importedWeather);
    then(weatherJpaRepository).should().saveAndFlush(mappedWeather);
  }

  @Test
  public void saveAndFlushThrowsNpe() {
    assertThatThrownBy(() -> weatherRepository.saveAndFlush(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("weather is required");

    then(weatherJpaRepository).shouldHaveNoInteractions();
    then(weatherEntityImportMapper).shouldHaveNoInteractions();
    then(weatherEntityMapper).shouldHaveNoInteractions();
  }

  @Test
  public void shouldGetLatest() {
    Integer locationId = 1;
    WeatherEntity weatherEntity = mock(WeatherEntity.class);
    given(weatherJpaRepository.getLatest(locationId)).willReturn(weatherEntity);

    Weather expectedWeather = mock(Weather.class);
    given(weatherEntityMapper.map(weatherEntity)).willReturn(expectedWeather);

    Weather actualWeather = weatherRepository.getLatest(locationId);

    assertThat(actualWeather).isEqualTo(expectedWeather);

    then(weatherJpaRepository).should().getLatest(locationId);
    then(weatherEntityMapper).should().map(weatherEntity);
  }

  @Test
  public void getLatestThrowsNpe() {
    assertThatThrownBy(() -> weatherRepository.getLatest(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(weatherJpaRepository).shouldHaveNoInteractions();
    then(weatherEntityMapper).shouldHaveNoInteractions();
  }

}
