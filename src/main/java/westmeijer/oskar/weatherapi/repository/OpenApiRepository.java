package westmeijer.oskar.weatherapi.repository;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.model.WeatherEntity;
import westmeijer.oskar.weatherapi.model.WeatherEntityBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class OpenApiRepository {

    private final WebClient webClient;

    private static final String OPEN_WEATHER_API_LUEBECK =
            "data/2.5/weather?id=2875601&units=metric&appid=d48670897d08c4876ce92adb0780d59b";

    private static final Logger logger = LoggerFactory.getLogger(OpenApiRepository.class);

    public OpenApiRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    public WeatherEntity requestOpenWeatherApi() {
        logger.info("Requesting OpenWeatherApi.");
        ObjectNode json = webClient.get().uri(OPEN_WEATHER_API_LUEBECK).retrieve().bodyToMono(ObjectNode.class).block();
        logger.debug(String.valueOf(json));
        long temp = json.path("main").path("temp").asLong();
        logger.info("Current temperature: {} - {}", temp, LocalDateTime.now());

        return new WeatherEntityBuilder().setId(UUID.randomUUID()).setTemperature(temp).setTimestamp(LocalDateTime.now())
                .createWeatherEntity();
    }

}
