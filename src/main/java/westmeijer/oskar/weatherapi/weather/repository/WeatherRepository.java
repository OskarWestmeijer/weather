package westmeijer.oskar.weatherapi.weather.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getLatestEntries(String localZipCode);

  List<Weather> getLastThreeDays(String localZipCode);

  Weather saveAndFlush(Weather weather);

}
