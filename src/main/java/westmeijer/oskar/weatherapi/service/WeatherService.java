package westmeijer.oskar.weatherapi.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.repository.WeatherEntity;
import westmeijer.oskar.weatherapi.repository.WeatherRepository;

import java.util.List;

@Service
public class WeatherService {

    private static final String OPEN_WEATHER_API_LUEBECK =
            "data/2.5/weather?id=2875601&units=metric&appid=d48670897d08c4876ce92adb0780d59b";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WebClient webClient;

    private final WeatherRepository weatherRepository;

    public WeatherService(WebClient webClient,
            WeatherRepository weatherRepository) {
        this.webClient = webClient;
        this.weatherRepository = weatherRepository;
    }

    public List<WeatherEntity> getWeather() {
        List<WeatherEntity> weatherEntities = (List<WeatherEntity>) weatherRepository.findAll();
        return weatherEntities;
    }

    private void requestOpenWeatherApi() {
        logger.info("Requesting OpenWeatherApi");
        ObjectNode json = webClient.get().uri(OPEN_WEATHER_API_LUEBECK).retrieve().bodyToMono(ObjectNode.class).block();
        logger.info(String.valueOf(json));
        String temp = json.path("main").path("temp").asText();
        logger.info(temp);
    }

    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() {
        requestOpenWeatherApi();
        logger.info("Refreshed weather!");
    }

}
