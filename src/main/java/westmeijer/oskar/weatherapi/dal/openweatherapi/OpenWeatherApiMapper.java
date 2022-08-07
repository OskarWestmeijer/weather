package westmeijer.oskar.weatherapi.dal.openweatherapi;

import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.util.UUID;

public class OpenWeatherApiMapper {

    /**
     * Maps OpenApi Response to Entity object.
     *
     * @param apiResponse to be mapped
     * @return response
     */
    public static Weather map(OpenWeatherApiResponse apiResponse) {
        Preconditions.checkNotNull(apiResponse);
        return new Weather(UUID.randomUUID(), apiResponse.getTemperature(), Instant.now());
    }


}
