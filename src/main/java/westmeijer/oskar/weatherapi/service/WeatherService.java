package westmeijer.oskar.weatherapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.controller.WeatherController;

import javax.annotation.PostConstruct;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private Weather weather;

    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() {
        logger.info("Refreshing weather!");
        weather = new Weather(weather.getTemperature() + 1);
    }

    @PostConstruct
    public void initWeather() {
        logger.info("Init weather!");
        weather = new Weather(1);
    }

    public Weather getWeather() {
        return weather;
    }
}
