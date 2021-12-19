package westmeijer.oskar.weatherapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
public class WeatherService {

    private static final String OPEN_WEATHER_API_LUEBECK = "data/2.5/weather?id=2875601&units=metric&appid=d48670897d08c4876ce92adb0780d59b";

    private static final String TEST_API = "data/test";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private Weather weather;

    WebClient webClient;

    public WeatherService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void requestOpenWeatherApi() {
        logger.info("Requesting OpenWeatherApi");
        ObjectNode json = webClient.get().uri(OPEN_WEATHER_API_LUEBECK).retrieve().bodyToMono(ObjectNode.class).block();
        logger.info(String.valueOf(json));
        String temp = json.path("main").path("temp").asText();
        logger.info(temp);
    }

    public void requestTestApi() {
        webClient.get().uri(TEST_API).retrieve();

    }


    @Scheduled(fixedDelay = 60000)
    public void refreshWeather() {
        weather = new Weather(weather.getTemperature() + 1);
        logger.info("Refreshed weather! Temperature: {}", weather.getTemperature());
    }

    @PostConstruct
    public void initWeather() {
        logger.trace("Init weather!");
        weather = new Weather(1);
    }

    public Weather getWeather() {
        return weather;
    }
}
