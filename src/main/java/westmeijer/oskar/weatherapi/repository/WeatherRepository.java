package westmeijer.oskar.weatherapi.repository;

import java.time.Instant;
import java.util.List;
import westmeijer.oskar.weatherapi.service.model.Weather;

public interface WeatherRepository {

  List<Weather> getLatestEntries(String localZipCode);

  List<Weather> getLastThreeDays(String localZipCode);

  List<Weather> getSpecificDay(String localZipCode, Instant start, Instant end);

  Weather saveAndFlush(Weather weather);

}
