package westmeijer.oskar.weatherapi.web;

import com.google.common.base.Preconditions;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static westmeijer.oskar.weatherapi.WeatherApiApplication.TIMEZONE_GERMANY;

public class WeatherMapper {

    public static WeatherDTO map(String zipCode, List<Weather> weather) {
        Preconditions.checkNotNull(weather);
        Preconditions.checkNotNull(zipCode);
        return new WeatherDTO(zipCode, ZonedDateTime.now(TIMEZONE_GERMANY), weather);
    }

}
