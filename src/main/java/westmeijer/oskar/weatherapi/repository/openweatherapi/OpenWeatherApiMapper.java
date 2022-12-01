package westmeijer.oskar.weatherapi.repository.openweatherapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class OpenWeatherApiMapper {

    /**
     * Maps OpenWeatherApi Response to Entity object.
     */
    public static Weather map(OpenWeatherApiResponse response, int zipCode) {
        Preconditions.checkNotNull(response);
        double temperature = response.getMain().getTemperature();
        int humidity = response.getMain().getHumidity();
        double windSpeed = response.getWind().getWindSpeed();

        return new Weather(UUID.randomUUID(), temperature, Instant.now().truncatedTo(ChronoUnit.SECONDS), humidity, windSpeed, zipCode);
    }


}
