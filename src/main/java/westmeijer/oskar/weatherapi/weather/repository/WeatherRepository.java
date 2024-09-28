package westmeijer.oskar.weatherapi.weather.repository;

import java.time.Instant;
import java.util.List;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getWeather(Integer locationId, Instant from, Integer limit);

  List<Weather> getLast24h(Integer locationId);

  Weather getLatest(Integer locationId);
}
