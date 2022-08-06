package westmeijer.oskar.weatherapi.web;

import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.web.WeatherDTO;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherTransformer {

    public static WeatherDTO map(String zipCode, List<Weather> weather) {

        return new WeatherDTO(zipCode, LocalDateTime.now(), weather);

    }

}
