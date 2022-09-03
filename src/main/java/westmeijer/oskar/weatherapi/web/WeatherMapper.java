package westmeijer.oskar.weatherapi.web;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.util.List;


public class WeatherMapper {

    private static final Logger logger = LoggerFactory.getLogger(WeatherMapper.class);


    public static WeatherDTO map(Location location, List<Weather> weather) {
        Preconditions.checkNotNull(weather);
        Preconditions.checkNotNull(location);

        return new WeatherDTO(location, Instant.now(), weather);
    }

}
