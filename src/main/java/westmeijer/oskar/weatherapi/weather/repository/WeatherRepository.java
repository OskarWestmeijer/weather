package westmeijer.oskar.weatherapi.weather.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getLast24h(Integer locationId);

  Weather saveAndFlush(Weather weather);

  Weather getLatest(Integer locationId);
}
