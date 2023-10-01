package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.service.model.ImportJobLocation;
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
  @Transactional
  public void refreshWeather() {
    try {
      meterRegistry.counter("import.job", "import", "execution").increment();

      ImportJobLocation location = locationService.getNextImportLocation();
      log.info("Import weather for location: {}", location);

      Weather importedWeather = openWeatherApiClient.requestWithGeneratedClient(location);
      Weather savedWeather = weatherService.saveAndFlush(importedWeather);
      locationService.updateLastImportAt(location);

      log.info("Saved imported weather: {}", savedWeather);
    } catch (OpenWeatherApiRequestException requestException) {
      log.error("OpenWeatherApi request failed!", requestException);
      meterRegistry.counter("import.job", "error", "request").increment();
    } catch (Exception generalException) {
      log.error("Exception during Import job.", generalException);
      meterRegistry.counter("import.job", "error", "process").increment();
    }

  }

}
