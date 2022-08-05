package westmeijer.oskar.weatherapi.openweatherapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.model.Weather;
import westmeijer.oskar.weatherapi.model.WeatherBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Repository
public class OpenWeatherApiClient {

    private final WebClient webClient;


    private final String urlPathLuebeck;

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherApiClient.class);

    public OpenWeatherApiClient(WebClient webClient, @Value("${openweatherapi.urlPath}") String urlPathLuebeck) {
        this.webClient = webClient;
        this.urlPathLuebeck = urlPathLuebeck;
    }

    /**
     * Requests the public OpenWeatherApi.
     *
     * @return WeatherEntity - object containing current weather
     */
    public Weather requestCurrentWeather() {
        logger.info("Requesting OpenWeatherApi.");
        OpenWeatherApiResponse apiResponse = webClient.get().uri(urlPathLuebeck).retrieve().bodyToMono(OpenWeatherApiResponse.class).block();
        logger.info(String.valueOf(apiResponse));
        return map(apiResponse);
    }

    /**
     * Maps OpenApi Response to Entity object.
     *
     * @param apiResponse to be mapped
     * @return response
     */
    private Weather map(OpenWeatherApiResponse apiResponse) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        logger.info("Current weather: {} - {}", apiResponse, time);
        return new WeatherBuilder().setId(UUID.randomUUID()).setTemperature(apiResponse.getTemperature()).setTimestamp(time)
                .createWeatherEntity();
    }

}
