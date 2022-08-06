package westmeijer.oskar.weatherapi.dal.openweatherapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.entity.Weather;

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
        logger.debug(String.valueOf(apiResponse));
        return OpenWeatherApiMapper.map(apiResponse);
    }


}
