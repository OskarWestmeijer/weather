package westmeijer.oskar.weatherapi.weather.controller;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.WeatherApi;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class WeatherController implements WeatherApi {

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final WeatherDtoMapper weatherDtoMapper;

  @Override
  public ResponseEntity<WeatherResponse> getWeatherFeedForLocation(Integer locationId, Instant from, Integer limit) {
    log.info("Received Weather request. locationId: {}, from: {}, limit: {}", locationId, from, limit);
    requireNonNull(locationId, "locationId is required");

    Location location = locationService.getByIdOmitWeather(locationId);
    WeatherFeedPage weatherFeedPage = weatherService.getWeatherFeedPage(locationId, from, limit);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherFeedPage);
    log.info("Weather response. locationId: {}, weatherCount: {}", locationId, weatherResponse.getWeatherData().size());
    return ResponseEntity.ok(weatherResponse);
  }

}
