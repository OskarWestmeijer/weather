package westmeijer.oskar.weatherapi.repository;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.model.WeatherEntity;
import westmeijer.oskar.weatherapi.model.WeatherEntityBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Repository
public class OpenApiClient {

    private final WebClient webClient;

    private static final String OPEN_WEATHER_API_LUEBECK =
            "data/2.5/weather?id=2875601&units=metric&appid=d48670897d08c4876ce92adb0780d59b";

    private static final Logger logger = LoggerFactory.getLogger(OpenApiClient.class);

    public OpenApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Requests the public OpenWeatherApi.
     *
     * @return
     */
    public WeatherEntity requestOpenWeatherApi() {
        logger.info("Requesting OpenWeatherApi.");
        ObjectNode json = webClient.get().uri(OPEN_WEATHER_API_LUEBECK).retrieve().bodyToMono(ObjectNode.class).block();
        logger.debug(String.valueOf(json));
        long temp = json.path("main").path("temp").asLong();
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        logger.info("Current temperature: {} - {}", temp, time);

        return new WeatherEntityBuilder().setId(UUID.randomUUID()).setTemperature(temp).setTimestamp(time)
                .createWeatherEntity();
    }

}
