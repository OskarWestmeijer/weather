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
import westmeijer.oskar.weatherapi.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.service.LocationService;
import westmeijer.oskar.weatherapi.service.WeatherService;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{localZipCode}")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

  private final WeatherService weatherService;

  private final LocationService locationService;

  private final WeatherDtoMapper weatherDtoMapper;


  @GetMapping("/now")
  public ResponseEntity<WeatherResponse> getNow(@PathVariable String localZipCode) {
    log.info("Received Weather request NOW for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    Weather weather = weatherService.getNow(location);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, Collections.singletonList(weather));
    return ResponseEntity.ok(weatherResponse);
  }

  @GetMapping("/24h")
  public ResponseEntity<WeatherResponse> getLast24Hours(@PathVariable String localZipCode) {
    log.info("Received Weather request 24h for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    List<Weather> weatherList = weatherService.getLast24h(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);

  }

  @GetMapping("/3d")
  public ResponseEntity<WeatherResponse> getLast3Days(@PathVariable String localZipCode) {
    log.info("Received Weather request 3d for localZipCode: {}", localZipCode);
    Location location = locationService.findById(localZipCode);
    List<Weather> weatherList = weatherService.getLast3Days(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

  /**
   * Get weather for a certain date and location.
   *
   * @param localZipCode - location to be requested
   * @param date         - expected format: dd-MM-YYYY
   * @return
   */
  @GetMapping("/{date}")
  public ResponseEntity<WeatherResponse> getSpecificDate(@PathVariable String localZipCode, @PathVariable String date) {
    log.info("Received Weather request SPECIFIC date for localZipCode: {}, date: {}", localZipCode, date);
    Location location = locationService.findById(localZipCode);
    Instant instant = ControllerUtil.atStartOfDay(date, location);
    List<Weather> weatherList = weatherService.getSpecificDate(localZipCode, instant);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(location, weatherList);
    return ResponseEntity.ok(weatherResponse);
  }

}
