package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

@Component
public class WeatherImportJob {

  private static final Logger logger = LoggerFactory.getLogger(WeatherImportJob.class);

  private final Counter importError;
  private final Counter importExecution;

  private final WeatherJpaRepository weatherJpaRepository;
  private final LocationJpaRepository locationJpaRepository;

  private final OpenWeatherApiClient openWeatherApiClient;

  public WeatherImportJob(MeterRegistry meterRegistry, WeatherJpaRepository weatherJpaRepository,
      LocationJpaRepository locationJpaRepository, OpenWeatherApiClient openWeatherApiClient) {
    this.importError = meterRegistry.counter("job", "import", "error");
    this.importExecution = meterRegistry.counter("job", "import", "execution");
    this.weatherJpaRepository = weatherJpaRepository;
    this.locationJpaRepository = locationJpaRepository;
    this.openWeatherApiClient = openWeatherApiClient;
  }


  @Scheduled(fixedDelay = 60000)
  public void refreshWeather() {
    try {
      importExecution.increment();
      LocationEntity locationEntity = locationJpaRepository.findFirstByOrderByLastImportAtAsc();
      Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
      locationEntity.setModifiedAt(now);
      locationEntity.setLastImportAt(now);
      logger.info("Request for location: {}", locationEntity);
      WeatherEntity weatherEntity = openWeatherApiClient.requestWeather(locationEntity);
      WeatherEntity savedWeatherEntity = weatherJpaRepository.saveAndFlush(weatherEntity);
      logger.info("Saved weather entity: {}", savedWeatherEntity);
      locationJpaRepository.saveAndFlush(locationEntity);
    } catch (OpenWeatherApiRequestException requestException) {
      logger.error("OpenWeatherApi request failed!", requestException);
      importError.increment();
    } catch (Exception generalException) {
      logger.error("Exception during Import job.", generalException);
      importError.increment();
    }
  }

}
