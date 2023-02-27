package westmeijer.oskar.weatherapi.controller.model;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.util.List;


public class WeatherMapper {

    private static final Logger logger = LoggerFactory.getLogger(WeatherMapper.class);


    public static WeatherResponse map(Location location, List<Weather> weather) {
        Preconditions.checkNotNull(weather);
        Preconditions.checkNotNull(location);

        return new WeatherResponse(location, Instant.now(), weather);
    }

}
