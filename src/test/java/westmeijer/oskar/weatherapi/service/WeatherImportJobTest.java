package westmeijer.oskar.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class WeatherImportJobTest {

  private final MeterRegistry meterRegistry = new SimpleMeterRegistry();

  private OpenWeatherApiClient openWeatherApiClient;

  private LocationService locationService;

  private WeatherService weatherService;

  private WeatherImportJob weatherImportJob;

  @BeforeEach
  public void init() {
    meterRegistry.clear();
    openWeatherApiClient = mock(OpenWeatherApiClient.class);
    locationService = mock(LocationService.class);
    weatherService = mock(WeatherService.class);
    weatherImportJob = new WeatherImportJob(meterRegistry, weatherService, locationService, openWeatherApiClient);
  }

  @Test
  public void shouldImportWeather() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    Location importLocation = new Location(
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        now
    );
    given(locationService.getNextImportLocation()).willReturn(importLocation);

    Weather importedWeather = new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, 88, 11.66, "23552", now);
    given(openWeatherApiClient.requestWeather(any(Location.class))).willReturn(importedWeather);

    Weather savedWeather = new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, 88, 11.66, "23552", now);
    given(weatherService.saveAndFlush(importedWeather)).willReturn(savedWeather);

    weatherImportJob.refreshWeather();

    assertThat(meterRegistry.counter("job", "import", "execution").count()).isEqualTo(1.00d);
    assertThat(meterRegistry.counter("job", "import", "error").count()).isEqualTo(0.00d);

    then(locationService).should().getNextImportLocation();
    then(openWeatherApiClient).should().requestWeather(any(Location.class));
    then(weatherService).should().saveAndFlush(importedWeather);
    then(locationService).should().saveAndFlush(any(Location.class));
  }

  @Test
  public void refreshWeather_throwExceptionOnApiFailure() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    Location importLocation = new Location(
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        now
    );
    given(locationService.getNextImportLocation()).willReturn(importLocation);
    given(openWeatherApiClient.requestWeather(any(Location.class))).willThrow(OpenWeatherApiRequestException.class);

    weatherImportJob.refreshWeather();

    then(locationService).should().getNextImportLocation();
    then(openWeatherApiClient).should().requestWeather(any(Location.class));
    then(weatherService).shouldHaveNoInteractions();
    then(locationService).should(never()).saveAndFlush(any(Location.class));
  }


}
