package westmeijer.oskar.weatherapi.dal.openweatherapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OpenWeatherApiMapper {

    /**
     * Maps OpenWeatherApi Response to Entity object.
     *
     * @param responseJson to be mapped
     * @return response
     */
    public static Weather map(ObjectNode responseJson) {
        Preconditions.checkNotNull(responseJson);

        Double temperature = responseJson.path("main").path("temp").asDouble();
        Integer humidity = responseJson.path("main").path("humidity").asInt();
        Double windSpeed = responseJson.path("wind").path("speed").asDouble();
        Integer locationCode = responseJson.path("id").asInt();

        return new Weather(UUID.randomUUID(), temperature, Instant.now(), humidity, windSpeed, locationCode);
    }


}
