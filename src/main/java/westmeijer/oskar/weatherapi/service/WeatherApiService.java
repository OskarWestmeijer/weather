package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class WeatherApiService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    private final OpenWeatherApiClient openWeatherApiClient;

    private final WeatherRepository weatherRepository;

    public WeatherApiService(OpenWeatherApiClient openWeatherApiClient, WeatherRepository weatherRepository) {
        this.openWeatherApiClient = openWeatherApiClient;
        this.weatherRepository = weatherRepository;
    }


    /**
     * Retrieves weather of the last 24h from the database.
     *
     * @param zipCode - get weather for this location
     * @return
     */
    public List<Weather> getLast24h(int zipCode) {
        List<Weather> weatherData = weatherRepository.getLatestEntries(zipCode);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieves weather of the last 3 days from the database.
     *
     * @param zipCode - get weather for this location
     * @return
     */
    public List<Weather> getLast3Days(int zipCode) {
        List<Weather> weatherData = weatherRepository.getLastThreeDays(zipCode);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieve weather for the provided date.
     *
     * @param zipCode
     * @param start   instant at start of day for zoneId
     * @return
     */
    public List<Weather> getSpecificDate(int zipCode, Instant start) {
        Instant end = start.plus(1L, ChronoUnit.DAYS);
        logger.debug("start instant: {}", start);
        logger.debug("end instant: {}", end);

        List<Weather> weatherData = weatherRepository.getSpecificDay(zipCode, start, end);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieves current weather from OpenWeatherApi, without storing the response in the database.
     *
     * @param location
     * @return
     */
    public Weather getNow(Location location) {
        return openWeatherApiClient.requestWeather(location);
    }

}
