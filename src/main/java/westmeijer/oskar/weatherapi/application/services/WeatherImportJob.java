package westmeijer.oskar.weatherapi.application.services;

import static java.util.Objects.requireNonNull;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.application.ports.inbound.ImportWeatherUseCase;
import westmeijer.oskar.weatherapi.application.ports.outbound.ImportWeatherClient;
import westmeijer.oskar.weatherapi.domain.exception.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.domain.model.Location;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherImportJob implements ImportWeatherUseCase {

  private final MeterRegistry meterRegistry;

  private final LocationService locationService;

  private final ImportWeatherClient importWeatherClient;

  @Scheduled(fixedDelay = 60000)
  @Transactional
  public void refreshWeather() {
    try {
      ThreadContext.clearAll();
      ThreadContext.put("traceId", generateTraceId());
      meterRegistry.counter("import.job", "import", "execution").increment();

      Location location = requireNonNull(locationService.getNextImportLocation(),
          "location is required");
      log.info("Import weather for location: {}, last_imported_at: {}", location,
          location.lastImportAt());

      Location updatedLocation = importWeatherClient.importLatestWeather(location);
      log.info("Imported weather: {}", updatedLocation.weather());

      locationService.save(updatedLocation);
    } catch (OpenWeatherApiRequestException requestException) {
      log.error("OpenWeatherApi request failed!", requestException);
      meterRegistry.counter("import.job", "error", "request").increment();
    } catch (Exception generalException) {
      log.error("Exception during Import job.", generalException);
      meterRegistry.counter("import.job", "error", "process").increment();
    } finally {
      ThreadContext.clearAll();
    }

  }

  private String generateTraceId() {
    return "weather_api_%s".formatted(Integer.toHexString(
        ThreadLocalRandom.current().nextInt())
    );
  }

}
