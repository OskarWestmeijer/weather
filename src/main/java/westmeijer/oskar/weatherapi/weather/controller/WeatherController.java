package westmeijer.oskar.weatherapi.weather.controller;

import static java.util.Objects.requireNonNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
      @NotNull @Parameter(name = "locationId", description = "Location to request weather from", required = true, in = ParameterIn.QUERY)
      @Valid @RequestParam(value = "locationId", required = true) Integer locationId,
      @Parameter(name = "from", description = "Weather starting from timestamp", in = ParameterIn.QUERY)
      @Valid @RequestParam(value = "from", required = false) Instant from,
      @Parameter(name = "limit", description = "Limit count of weather elements", in = ParameterIn.QUERY)
      @Valid @RequestParam(value = "limit", required = false) Integer limit
  ) {
    log.info("Received Weather request. locationId: {}, from: {}, limit: {}", locationId, from, limit);
    requireNonNull(locationId, "locationId is required");
    Instant fromTimestamp = Optional.ofNullable(from).orElse(Instant.EPOCH);
    int resultLimit = Optional.ofNullable(limit).orElse(1000);
    log.info("Enriched Weather request. locationId: {}, from: {}, limit: {}", locationId, fromTimestamp, resultLimit);

    Location location = locationService.getByIdOmitWeather(locationId);
    List<Weather> weatherList = weatherService.getWeather(locationId, fromTimestamp, resultLimit + 1);

    var totalRecordsCount = 0;
    boolean hasNewerRecords = false;
    Instant nextFrom = null;
    String nextLink = null;

    if (!weatherList.isEmpty()) {
      totalRecordsCount = weatherService.getTotalCount(locationId, fromTimestamp);

      if (weatherList.size() > resultLimit) {
        nextFrom = weatherList.getLast().recordedAt();
        weatherList.removeLast();
        hasNewerRecords = true;
        nextLink = "https://api.weather.oskar-westmeijer/weather?locationId=%s&from=%s&limit=%d".formatted(locationId, nextFrom,
            resultLimit);
      }
    }

    var pagingDetails = weatherDtoMapper.mapTo(weatherList.size(), totalRecordsCount, hasNewerRecords, nextFrom, nextLink);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList, pagingDetails);
    log.info("Weather response. locationId: {}, weatherCount: {}", locationId, weatherResponse.getWeatherData().size());
    return ResponseEntity.ok(weatherResponse);
  }

}
