package westmeijer.oskar.weatherapi.importjob.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.importjob.client.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.importjob.exception.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.importjob.service.WeatherImportJob;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.service.WeatherService;
import westmeijer.oskar.weatherapi.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class WeatherImportJobTest {

  private final MeterRegistry meterRegistry = new SimpleMeterRegistry();

  private final OpenWeatherApiClient openWeatherApiClient = mock(OpenWeatherApiClient.class);

  private final LocationService locationService = mock(LocationService.class);

  private final WeatherService weatherService = mock(WeatherService.class);

  private WeatherImportJob weatherImportJob;

  @BeforeEach
  public void init() {
    meterRegistry.clear();
    weatherImportJob = new WeatherImportJob(meterRegistry, weatherService, locationService, openWeatherApiClient);
  }

  @Test
  public void shouldImportWeather() {
    ImportJobLocation importLocation = mock(ImportJobLocation.class);

    given(locationService.getNextImportLocation()).willReturn(importLocation);

    Weather importedWeather = mock(Weather.class);
    given(openWeatherApiClient.requestWithGeneratedClient(importLocation)).willReturn(importedWeather);

    Weather savedWeather = mock(Weather.class);
    given(weatherService.saveAndFlush(importedWeather)).willReturn(savedWeather);

    weatherImportJob.refreshWeather();

    assertThat(meterRegistry.counter("import.job", "import", "execution").count()).isEqualTo(1.00d);
    assertThat(meterRegistry.counter("import.job", "error", "request").count()).isEqualTo(0.00d);
    assertThat(meterRegistry.counter("import.job", "error", "process").count()).isEqualTo(0.00d);

    then(locationService).should().getNextImportLocation();
    then(openWeatherApiClient).should().requestWithGeneratedClient(importLocation);
    then(weatherService).should().saveAndFlush(importedWeather);
    then(locationService).should().updateLastImportAt(importLocation);
  }

  @Test
  public void refreshWeather_throwExceptionOnApiFailure() {
    ImportJobLocation importLocation = mock(ImportJobLocation.class);
    given(locationService.getNextImportLocation()).willReturn(importLocation);
    given(openWeatherApiClient.requestWithGeneratedClient(importLocation)).willThrow(OpenWeatherApiRequestException.class);

    weatherImportJob.refreshWeather();

    assertThat(meterRegistry.counter("import.job", "import", "execution").count()).isEqualTo(1.00d);
    assertThat(meterRegistry.counter("import.job", "error", "request").count()).isEqualTo(1.00d);
    assertThat(meterRegistry.counter("import.job", "error", "process").count()).isEqualTo(0.00d);

    then(locationService).should().getNextImportLocation();
    then(openWeatherApiClient).should().requestWithGeneratedClient(importLocation);
    then(weatherService).shouldHaveNoInteractions();
    then(locationService).should(never()).updateLastImportAt(importLocation);
  }


}
