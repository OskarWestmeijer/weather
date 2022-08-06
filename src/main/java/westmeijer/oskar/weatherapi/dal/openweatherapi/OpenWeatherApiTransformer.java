package westmeijer.oskar.weatherapi.dal.openweatherapi;

import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class OpenWeatherApiTransformer {

    /**
     * Maps OpenApi Response to Entity object.
     *
     * @param apiResponse to be mapped
     * @return response
     */
    public static Weather transform(OpenWeatherApiResponse apiResponse) {
        Preconditions.checkNotNull(apiResponse);
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return new Weather(UUID.randomUUID(), apiResponse.getTemperature(), time);
    }


}
