package westmeijer.oskar.weatherapi.weather.controller;

import static java.util.Objects.requireNonNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<WeatherResponse> getWeatherFeedForLocation(
      @NotNull @Parameter(name = "locationId", description = "Location to request weather from", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "locationId", required = true) Integer locationId,
      @Parameter(name = "from", description = "Weather starting from timestamp", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false) Instant from,
      @Parameter(name = "limit", description = "Limit count of weather elements", in = ParameterIn.QUERY) @Valid @RequestParam(value = "limit", required = false) Integer limit
  ) {
    log.info("Received Weather request. locationId: {}, from: {}, limit: {}", locationId, from, limit);
    requireNonNull(locationId, "locationId is required");
    if (from == null) {
      from = Instant.EPOCH;
    }
    if (limit == null) {
      limit = 1000;
    }
    log.info("Enriched Weather request. locationId: {}, from: {}, limit: {}", locationId, from, limit);

    Location location = locationService.getByIdOmitWeather(locationId);
    List<Weather> weatherList = weatherService.getWeather(locationId, from, limit);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

}
