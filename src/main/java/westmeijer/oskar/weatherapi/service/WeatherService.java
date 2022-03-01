package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.repository.OpenApiRepository;
import westmeijer.oskar.weatherapi.model.WeatherEntity;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;

import java.util.List;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;

    private final OpenApiRepository openApiRepository;

    public WeatherService(WeatherRepository weatherRepository, OpenApiRepository openApiRepository) {
        this.weatherRepository = weatherRepository;
        this.openApiRepository = openApiRepository;
    }

    /**
     * Retrieve all Weather Entities from database.
     *
     * @return
     */
    public List<WeatherEntity> getWeather() {
        List<WeatherEntity> weatherEntities = (List<WeatherEntity>) weatherRepository.findAll();
        return weatherEntities;
    }

    /**
     * Retrieve and save new weather data from OpenApi.
     */
    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() {
        logger.info("Start refreshing weather.");
        WeatherEntity currentWeather = openApiRepository.requestOpenWeatherApi();
        weatherRepository.save(currentWeather);
        logger.info("Finish refreshing weather!");
    }

}
