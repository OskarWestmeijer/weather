package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import westmeijer.oskar.weatherapi.application.ports.inbound.GetLocationUseCase;
import westmeijer.oskar.weatherapi.application.ports.inbound.GetWeatherUseCase;
import westmeijer.oskar.weatherapi.domain.model.Location;
import westmeijer.oskar.weatherapi.domain.model.WeatherFeedPage;
import westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.mappers.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.openapi.server.api.WeatherApi;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class WeatherController implements WeatherApi {

  private final GetWeatherUseCase getWeatherUseCase;

  private final GetLocationUseCase getLocationUseCase;

  private final WeatherDtoMapper weatherDtoMapper;

  @Override
  public ResponseEntity<WeatherResponse> getWeatherFeedForLocation(Integer locationId, Instant from,
      Integer limit) {
    log.info("Received Weather request. locationId: {}, from: {}, limit: {}", locationId, from,
        limit);
    requireNonNull(locationId, "locationId is required");

    Location location = getLocationUseCase.getByIdOmitWeather(locationId);
    WeatherFeedPage weatherFeedPage = getWeatherUseCase.getWeatherFeedPage(locationId, from, limit);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherFeedPage);
    log.info("Weather response. locationId: {}, weatherCount: {}", locationId,
        weatherResponse.getWeatherData().size());
    return ResponseEntity.ok(weatherResponse);
  }

}
