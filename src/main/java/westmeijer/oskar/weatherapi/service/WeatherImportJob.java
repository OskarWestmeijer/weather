package westmeijer.oskar.weatherapi.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class WeatherImportJob {

    private static final Logger logger = LoggerFactory.getLogger(WeatherImportJob.class);

    private final Counter importError;
    private final Counter importExecution;

    private final WeatherRepository weatherRepository;
    private final LocationRepository locationRepository;

    private final OpenWeatherApiClient openWeatherApiClient;

    private final LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<>();

    public WeatherImportJob(MeterRegistry meterRegistry, WeatherRepository weatherRepository, LocationRepository locationRepository, OpenWeatherApiClient openWeatherApiClient) {
        this.importError = meterRegistry.counter("job", "import", "error");
        this.importExecution = meterRegistry.counter("job", "import", "execution");
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
        this.openWeatherApiClient = openWeatherApiClient;
    }


    /**
     * Job to request OpenWeatherApi every minute. The queue holds the locations to be requested.
     * On every run the latest location will be picked and in the end requeued to the end.
     * This job should never fail on exceptions.
     */
    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() throws InterruptedException {
        Location location = null;
        try {
            importExecution.increment();
            logger.info("Start weather import job.");
            location = locationQueue.take();
            logger.info("Request for location: {}", location);
            Weather weather = openWeatherApiClient.requestWeather(location);
            logger.info("Response with Weather: {}", weather);
            weatherRepository.save(weather);
        } catch (InterruptedException | NoSuchElementException ex1) {
            logger.error("Location queue exception!", ex1);
            importError.increment();
        } catch (OpenWeatherApiException ex2) {
            logger.error("OpenWeatherApi weather import request failed!", ex2);
            importError.increment();
        } finally {
            if (location != null) {
                locationQueue.put(location);
            }
            logger.info("Finish weather import job.");
        }
    }

    @PostConstruct
    private void fillLocationQueue() {
        List<Location> locationList = locationRepository.findAll();
        locationQueue.addAll(locationList);
    }

}
