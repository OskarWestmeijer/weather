package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class OpenWeatherApiMapper {

    /**
     * Maps OpenWeatherApi Response to Entity object.
     */
    public static Weather map(OpenWeatherApiResponse response, String localZipCode) {
        Preconditions.checkNotNull(response);
        double temperature = response.main().temperature();
        int humidity = response.main().humidity();
        double windSpeed = response.wind().windSpeed();

        return new Weather(UUID.randomUUID(), temperature, Instant.now().truncatedTo(ChronoUnit.SECONDS), humidity, windSpeed, localZipCode);
    }


}
