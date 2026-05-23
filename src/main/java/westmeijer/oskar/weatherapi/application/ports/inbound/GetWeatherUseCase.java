package westmeijer.oskar.weatherapi.application.ports.inbound;

import java.time.Instant;
import java.util.List;
import westmeijer.oskar.weatherapi.domain.model.Weather;
import westmeijer.oskar.weatherapi.domain.model.WeatherFeedPage;

public interface GetWeatherUseCase {

  int getTotalCount(Integer locationId, Instant from);

  WeatherFeedPage getWeatherFeedPage(Integer locationId, Instant from, Integer limit);

}
