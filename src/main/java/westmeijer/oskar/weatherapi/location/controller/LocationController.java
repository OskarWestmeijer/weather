package westmeijer.oskar.weatherapi.location.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import westmeijer.oskar.weatherapi.location.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.LocationsApi;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationResponse;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class LocationController implements LocationsApi {

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final LocationDtoMapper locationDtoMapper;

  private final WeatherDtoMapper weatherDtoMapper;

  @Override
  public ResponseEntity<LocationResponse> getLocations() {
    List<Location> locations = locationService.getAllOmitWeather();
    LocationResponse response = locationDtoMapper.mapToLocationResponse(locations);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<WeatherResponse> getWeatherFeedForLocation(
      @Parameter(name = "locationId", description = "api locationId", required = true, in = ParameterIn.PATH) @PathVariable("locationId") Integer locationId,
      @Parameter(name = "from", description = "Weather starting from timestamp", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false) Instant from,
      @Parameter(name = "limit", description = "Limit count of weather elements", in = ParameterIn.QUERY) @Valid @RequestParam(value = "limit", required = false) Integer limit
  ) {
    log.info("Received Weather request. locationId: {}, from: {}, limit: {}", locationId, from, limit);
    if (from == null) {
      from = Instant.EPOCH;
    }
    if (limit == null) {
      limit = 1000;
    }

    Location location = locationService.getByIdOmitWeather(locationId);
    List<Weather> weatherList = weatherService.getWeather(locationId, from, limit);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

}
