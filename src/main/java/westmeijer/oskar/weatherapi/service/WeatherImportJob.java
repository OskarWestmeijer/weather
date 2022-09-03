package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.repository.database.LocationRepository;
import westmeijer.oskar.weatherapi.repository.database.WeatherRepository;
import westmeijer.oskar.weatherapi.repository.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.repository.openweatherapi.OpenWeatherApiException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class WeatherImportJob {

    private static final Logger logger = LoggerFactory.getLogger(WeatherImportJob.class);

    private final WeatherRepository weatherRepository;
    private final LocationRepository locationRepository;

    private final OpenWeatherApiClient openWeatherApiClient;

    private final LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<>();

    public WeatherImportJob(WeatherRepository weatherRepository, LocationRepository locationRepository, OpenWeatherApiClient openWeatherApiClient) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
        this.openWeatherApiClient = openWeatherApiClient;
    }


    /**
     * Retrieve and save new weather data from OpenApi.
     */
    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() throws InterruptedException {
        Location location = null;
        try {
            logger.info("Start weather import job.");
            location = locationQueue.take();
            logger.info("Request for location: {}", location);
            Weather weather = openWeatherApiClient.requestWeather(location);
            logger.info("Location: {}, Weather: {}", location, weather);
            weatherRepository.save(weather);
        } catch (InterruptedException | NoSuchElementException ex1) {
            logger.error("Location queue exception!", ex1);
        } catch (OpenWeatherApiException ex2) {
            logger.error("OpenWeatherApi weather import request failed!", ex2);
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
