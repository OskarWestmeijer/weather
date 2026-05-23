package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest;

import static java.util.Objects.requireNonNull;

import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import westmeijer.oskar.weatherapi.application.services.LocationService;
import westmeijer.oskar.weatherapi.domain.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.WeatherApi;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.mappers.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.application.services.WeatherService;
import westmeijer.oskar.weatherapi.domain.model.WeatherFeedPage;

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
