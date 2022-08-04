package westmeijer.oskar.weatherapi.openapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.model.OpenApiResponse;
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
     * @return WeatherEntity - object containing current weather
     */
    public WeatherEntity requestCurrentWeather() {
        logger.info("Requesting OpenWeatherApi.");
        OpenApiResponse apiResponse = webClient.get().uri(OPEN_WEATHER_API_LUEBECK).retrieve().bodyToMono(OpenApiResponse.class).block();
        logger.info(String.valueOf(apiResponse));
        return map(apiResponse);
    }

    /**
     * Maps OpenApi Response to Entity object.
     *
     * @param apiResponse
     * @return
     */
    private WeatherEntity map(OpenApiResponse apiResponse) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        logger.info("Current weather: {} - {}", apiResponse, time);
        return new WeatherEntityBuilder().setId(UUID.randomUUID()).setTemperature(apiResponse.getTemperature()).setTimestamp(time)
                .createWeatherEntity();
    }

}
