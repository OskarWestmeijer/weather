package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.dal.database.WeatherRepository;
import westmeijer.oskar.weatherapi.dal.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;

    private final OpenWeatherApiClient openWeatherApiClient;

    public WeatherService(WeatherRepository weatherRepository, OpenWeatherApiClient openWeatherApiClient) {
        this.weatherRepository = weatherRepository;
        this.openWeatherApiClient = openWeatherApiClient;
    }

    /**
     * Retrieve all Weather Entities from database.
     *
     * @return list of dtos
     */
    public List<Weather> getLatestWeather() {
        List<Weather> weatherData = weatherRepository.getLatestEntries();

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    public List<Weather> getWeatherLastThreeDays() {
        List<Weather> weatherData = weatherRepository.getLastThreeDays();

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    public List<Weather> getSpecificWeather(Instant start) {
        Instant end = start.plus(1L, ChronoUnit.DAYS);
        logger.debug("start instant: {}", start);
        logger.debug("end instant: {}", end);

        List<Weather> weatherData = weatherRepository.getSpecificDay(start, end);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieve and save new weather data from OpenApi.
     */
    @Scheduled(fixedDelay = 300000)
    public void refreshWeather() {
        try {
            logger.info("Start refreshing weather.");
            Weather currentWeather = openWeatherApiClient.requestCurrentWeather();
            logger.info("Current weather: {}", currentWeather);
            weatherRepository.save(currentWeather);
            logger.info("Finish refreshing weather!");
        } catch (Exception e) {
            logger.error("OpenWeatherApi request failed!", e);
        }
    }


}
