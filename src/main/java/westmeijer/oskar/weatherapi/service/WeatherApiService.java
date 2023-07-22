package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class WeatherApiService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    private final OpenWeatherApiClient openWeatherApiClient;

    private final WeatherJpaRepository weatherJpaRepository;

    public WeatherApiService(OpenWeatherApiClient openWeatherApiClient, WeatherJpaRepository weatherJpaRepository) {
        this.openWeatherApiClient = openWeatherApiClient;
        this.weatherJpaRepository = weatherJpaRepository;
    }


    /**
     * Retrieves weather of the last 24h from the database.
     *
     * @param localZipCode - get weather for this location
     * @return
     */
    public List<WeatherEntity> getLast24h(String localZipCode) {
        List<WeatherEntity> weatherEntityData = weatherJpaRepository.getLatestEntries(localZipCode);

        return weatherEntityData.stream()
                .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieves weather of the last 3 days from the database.
     *
     * @param localZipCode - get weather for this location
     * @return
     */
    public List<WeatherEntity> getLast3Days(String localZipCode) {
        List<WeatherEntity> weatherEntityData = weatherJpaRepository.getLastThreeDays(localZipCode);

        return weatherEntityData.stream()
                .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieve weather for the provided date.
     *
     * @param localZipCode
     * @param start   instant at start of day for zoneId
     * @return
     */
    public List<WeatherEntity> getSpecificDate(String localZipCode, Instant start) {
        Instant end = start.plus(1L, ChronoUnit.DAYS);
        logger.debug("start instant: {}", start);
        logger.debug("end instant: {}", end);

        List<WeatherEntity> weatherEntityData = weatherJpaRepository.getSpecificDay(localZipCode, start, end);

        return weatherEntityData.stream()
                .sorted(Comparator.comparing(WeatherEntity::getRecordedAt).reversed())
                .toList();
    }

    /**
     * Retrieves current weather from OpenWeatherApi, without storing the response in the database.
     *
     * @param locationEntity
     * @return
     */
    public WeatherEntity getNow(LocationEntity locationEntity) {
        return openWeatherApiClient.requestWeather(locationEntity);
    }

}
