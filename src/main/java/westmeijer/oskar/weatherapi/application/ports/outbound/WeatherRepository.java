package westmeijer.oskar.weatherapi.application.ports.outbound;

import java.time.Instant;
import java.util.List;
import westmeijer.oskar.weatherapi.domain.model.Weather;

public interface WeatherRepository {

  List<Weather> getWeather(Integer locationId, Instant from, Integer limit);

  int getTotalCount(Integer locationId, Instant from);

}
