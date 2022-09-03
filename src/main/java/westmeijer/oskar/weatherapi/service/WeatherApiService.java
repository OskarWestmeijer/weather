package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.repository.database.WeatherRepository;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class WeatherApiService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    private final WeatherRepository weatherRepository;

    public WeatherApiService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    public List<Weather> getLast24h(int zipCode) {
        List<Weather> weatherData = weatherRepository.getLatestEntries(zipCode);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    public List<Weather> getLast3Days(int zipCode) {
        List<Weather> weatherData = weatherRepository.getLastThreeDays(zipCode);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

    public List<Weather> getSpecificDate(int zipCode, Instant start) {
        Instant end = start.plus(1L, ChronoUnit.DAYS);
        logger.debug("start instant: {}", start);
        logger.debug("end instant: {}", end);

        List<Weather> weatherData = weatherRepository.getSpecificDay(zipCode, start, end);

        return weatherData.stream()
                .sorted(Comparator.comparing(Weather::getRecordedAt).reversed())
                .toList();
    }

}
