package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.jpa.WeatherJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherImportJob {

  private final MeterRegistry meterRegistry;

  private final WeatherJpaRepository weatherJpaRepository;

  private final LocationService locationService;

  private final LocationJpaRepository locationJpaRepository;

  private final OpenWeatherApiClient openWeatherApiClient;


  @Scheduled(fixedDelay = 60000)
  public void refreshWeather() {
    try {
      meterRegistry.counter("job", "import", "execution").increment();

      Instant importStart = Instant.now().truncatedTo(ChronoUnit.MILLIS);
      LocationEntity locationEntity = locationJpaRepository.findFirstByOrderByLastImportAtAsc();
      locationEntity.setModifiedAt(importStart);
      locationEntity.setLastImportAt(importStart);
      log.info("Import weather for location: {}", locationEntity);

      WeatherEntity importedWeather = openWeatherApiClient.requestWeather(locationEntity);
      WeatherEntity savedWeatherEntity = weatherJpaRepository.saveAndFlush(importedWeather);
      log.info("Imported weather: {}", savedWeatherEntity);
      locationJpaRepository.saveAndFlush(locationEntity);

    } catch (OpenWeatherApiRequestException requestException) {
      log.error("OpenWeatherApi request failed!", requestException);
      meterRegistry.counter("job", "import", "error").increment();
    } catch (Exception generalException) {
      log.error("Exception during Import job.", generalException);
      meterRegistry.counter("job", "import", "error").increment();
    }

  }

}
