package westmeijer.oskar.weatherapi.repository.openweatherapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import lombok.Data;


/**
 * Has the same structure as the actual json body.
 */
@Data
public class OpenWeatherApiResponse {


    private final Main main;
    private final Wind wind;


    @JsonCreator
    public OpenWeatherApiResponse(@JsonProperty("main") Main main,
                                  @JsonProperty("wind") Wind wind) {
        Preconditions.checkNotNull(main);
        Preconditions.checkNotNull(wind);

        this.main = main;
        this.wind = wind;
    }

    @Data
    static class Main {

        private final double temperature;
        private final int humidity;

        @JsonCreator
        public Main(@JsonProperty("temp") double temperature,
                    @JsonProperty("humidity") int humidity) {
            this.temperature = temperature;
            this.humidity = humidity;
        }
    }

    @Data
    static class Wind {

        private final double windSpeed;

        @JsonCreator
        public Wind(@JsonProperty("speed") double windSpeed) {
            this.windSpeed = windSpeed;
        }

    }

}
