package westmeijer.oskar.weatherapi.weather.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getLast24h(String localZipCode);

  List<Weather> getLast3Days(String localZipCode);

  Weather saveAndFlush(Weather weather);

  Weather getLatest(Location location);
}
