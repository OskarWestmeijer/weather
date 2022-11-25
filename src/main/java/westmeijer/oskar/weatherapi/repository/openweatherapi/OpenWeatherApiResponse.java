package westmeijer.oskar.weatherapi.repository.openweatherapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.Objects;

public class OpenWeatherApiResponse {


    private final double temperature;

    private final double windSpeed;

    private final int humidity;

    @JsonCreator
    public OpenWeatherApiResponse(@JsonProperty("main") Map<String, Object> main,
                                  @JsonProperty("wind") Map<String, Object> wind) {
        Preconditions.checkNotNull(main);
        Preconditions.checkNotNull(wind);

        this.temperature = (double) main.get("temp");
        this.humidity = (int) main.get("humidity");
        this.windSpeed = (double) wind.get("speed");
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenWeatherApiResponse response = (OpenWeatherApiResponse) o;
        return Double.compare(response.temperature, temperature) == 0 && Double.compare(response.windSpeed, windSpeed) == 0 && humidity == response.humidity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, windSpeed, humidity);
    }

    @Override
    public String toString() {
        return "OpenWeatherApiResponse{" +
                "temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                '}';
    }
}
