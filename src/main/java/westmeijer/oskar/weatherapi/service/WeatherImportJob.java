package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.repository.database.WeatherRepository;
import westmeijer.oskar.weatherapi.repository.openweatherapi.OpenWeatherApiClient;

@Component
public class WeatherImportJob {

    private static final Logger logger = LoggerFactory.getLogger(WeatherImportJob.class);

    private final WeatherRepository weatherRepository;

    private final OpenWeatherApiClient openWeatherApiClient;

    public WeatherImportJob(WeatherRepository weatherRepository, OpenWeatherApiClient openWeatherApiClient) {
        this.weatherRepository = weatherRepository;
        this.openWeatherApiClient = openWeatherApiClient;
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
