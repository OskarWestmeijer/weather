package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherImportJob {

  private final MeterRegistry meterRegistry;

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final OpenWeatherApiClient openWeatherApiClient;


  @Scheduled(fixedDelay = 60000)
  public void refreshWeather() {
    try {
      meterRegistry.counter("job", "import", "execution").increment();

      Location location = withImportTs(locationService.getNextImportLocation());
      log.info("Import weather for location: {}", location);

      Weather importedWeather = openWeatherApiClient.requestWeather(location);
      Weather savedWeatherEntity = weatherService.saveAndFlush(importedWeather);
      locationService.saveAndFlush(location);
      log.info("Saved imported weather: {}", savedWeatherEntity);

    } catch (OpenWeatherApiRequestException requestException) {
      log.error("OpenWeatherApi request failed!", requestException);
      meterRegistry.counter("job", "import", "error").increment();
    } catch (Exception generalException) {
      log.error("Exception during Import job.", generalException);
      meterRegistry.counter("job", "import", "error").increment();
    }

  }

  private Location withImportTs(Location location) {
    Instant importStart = Instant.now().truncatedTo(ChronoUnit.MICROS);
    return new Location(location.localZipCode(), location.locationCode(), location.cityName(), location.country(), importStart);
  }

}
