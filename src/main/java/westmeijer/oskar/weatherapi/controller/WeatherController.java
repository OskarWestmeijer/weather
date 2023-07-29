package westmeijer.oskar.weatherapi.controller;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.openapi.api.WeatherApi;
import westmeijer.oskar.openapi.model.WeatherResponse;
import westmeijer.oskar.weatherapi.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.service.LocationService;
import westmeijer.oskar.weatherapi.service.WeatherService;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class WeatherController implements WeatherApi {

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final WeatherDtoMapper weatherDtoMapper;


  @Override
  public ResponseEntity<WeatherResponse> getLatestWeather(@PathVariable String localZipCode) {
    log.info("Received Weather request NOW for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    Weather weather = weatherService.getNow(location);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, Collections.singletonList(weather));
    return ResponseEntity.ok(weatherResponse);
  }

  @Override
  public ResponseEntity<WeatherResponse> getWeatherLast24Hours(@PathVariable String localZipCode) {
    log.info("Received Weather request 24h for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    List<Weather> weatherList = weatherService.getLast24h(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);

  }

  @Override
  public ResponseEntity<WeatherResponse> getWeatherLast3Days(@PathVariable String localZipCode) {
    log.info("Received Weather request 3d for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    List<Weather> weatherList = weatherService.getLast3Days(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

  @Override
  public ResponseEntity<WeatherResponse> getSpecificWeather(@PathVariable String localZipCode, @PathVariable String date) {
    log.info("Received Weather request SPECIFIC date for localZipCode: {}, date: {}", localZipCode, date);
    Location location = locationService.findById(localZipCode);
    Instant instant = ControllerUtil.atStartOfDay(date, location);
    List<Weather> weatherList = weatherService.getSpecificDate(localZipCode, instant);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

}
