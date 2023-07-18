package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;

@Component
public class WeatherImportJob {

  private static final Logger logger = LoggerFactory.getLogger(WeatherImportJob.class);

  private final Counter importError;
  private final Counter importExecution;

  private final WeatherRepository weatherRepository;
  private final LocationRepository locationRepository;

  private final OpenWeatherApiClient openWeatherApiClient;

  private final LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<>();

  public WeatherImportJob(MeterRegistry meterRegistry, WeatherRepository weatherRepository,
      LocationRepository locationRepository, OpenWeatherApiClient openWeatherApiClient) {
    this.importError = meterRegistry.counter("job", "import", "error");
    this.importExecution = meterRegistry.counter("job", "import", "execution");
    this.weatherRepository = weatherRepository;
    this.locationRepository = locationRepository;
    this.openWeatherApiClient = openWeatherApiClient;
  }


  /**
   * Job to request OpenWeatherApi every minute. The queue holds the locations to be requested. On
   * every run the latest location will be picked and in the end requeued to the end.
   */
  @Scheduled(fixedDelay = 60000)
  public void refreshWeather() {
    try {
      importExecution.increment();
      logger.info("Start weather import job.");
      Location location = locationQueue.take();
      logger.info("Request for location: {}", location);
      Weather weather = openWeatherApiClient.requestWeather(location);
      logger.info("Response with Weather: {}", weather);
      weatherRepository.save(weather);
      logger.info("Finish weather import job.");
    } catch (OpenWeatherApiRequestException requestException) {
      logger.error("OpenWeatherApi request failed!", requestException);
      importError.increment();
    } catch (Exception generalException) {
      logger.error("Exception during Import job. Stop scheduled job and read logs for details.",
          generalException);
      importError.increment();
      throw new RuntimeException(
          "Exception during Import job. Stop scheduled job and read logs for details.");
    }
  }

  @PostConstruct
  private void fillLocationQueue() {
    List<Location> locationList = locationRepository.findAll();
    locationQueue.addAll(locationList);
  }

}
