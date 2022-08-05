package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.model.WeatherDTO;
import westmeijer.oskar.weatherapi.model.WeatherDTOBuilder;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.model.Weather;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;

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
    public List<WeatherDTO> getWeather() {
        List<Weather> weatherEntities = weatherRepository.getLatestEntries();

        return weatherEntities.stream()
                .map(this::map)
                .sorted(Comparator.comparing(WeatherDTO::getTimestamp).reversed())
                .toList();
    }

    private WeatherDTO map(Weather weather) {
        return new WeatherDTOBuilder().setId(weather.getId())
                .setTemperature(weather.getTemperature()).setTimestmap(weather.getTimestamp()).createWeatherDTO();
    }

    /**
     * Retrieve and save new weather data from OpenApi.
     */
    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() {
        try {
            logger.info("Start refreshing weather.");
            Weather currentWeather = openWeatherApiClient.requestCurrentWeather();
            weatherRepository.save(currentWeather);
            logger.info("Finish refreshing weather!");
        } catch (Exception e) {
            logger.error("OpenApi request failed!", e);
        }
    }

}
