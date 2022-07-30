package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.openapi.OpenApiClient;
import westmeijer.oskar.weatherapi.model.WeatherEntity;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;

import java.util.List;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;

    private final OpenApiClient openApiClient;

    public WeatherService(WeatherRepository weatherRepository, OpenApiClient openApiClient) {
        this.weatherRepository = weatherRepository;
        this.openApiClient = openApiClient;
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
        try {
            logger.info("Start refreshing weather.");
            WeatherEntity currentWeather = openApiClient.requestCurrentWeather();
            weatherRepository.save(currentWeather);
            logger.info("Finish refreshing weather!");
        } catch (Exception e) {
            logger.error("OpenApi request failed!", e);
        }
    }

}
