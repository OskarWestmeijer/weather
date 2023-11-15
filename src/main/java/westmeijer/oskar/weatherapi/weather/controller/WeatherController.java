package westmeijer.oskar.weatherapi.weather.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.WeatherApi;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class WeatherController implements WeatherApi {

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final WeatherDtoMapper weatherDtoMapper;

  @Override
  public ResponseEntity<WeatherResponse> getWeatherLast24Hours(@PathVariable Integer locationId) {
    log.info("Received Weather request 24h for locationId: {}", locationId);
    Location location = locationService.getById(locationId);
    List<Weather> weatherList = weatherService.getLast24h(locationId);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

}
