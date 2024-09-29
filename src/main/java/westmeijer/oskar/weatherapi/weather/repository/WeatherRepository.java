package westmeijer.oskar.weatherapi.weather.repository;

import java.time.Instant;
import java.util.List;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getWeather(Integer locationId, Instant from, Integer limit);

  int getTotalCount(Integer locationId, Instant from);

}
