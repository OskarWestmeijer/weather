package westmeijer.oskar.weatherapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.service.WeatherService;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{localZipCode}")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

  private final WeatherService weatherService;

  private final LocationJpaRepository locationJpaRepository;

  private final WeatherDtoMapper weatherDtoMapper;


  @GetMapping("/now")
  public ResponseEntity<WeatherResponse> getNow(@PathVariable String localZipCode) {
    log.info("Received Weather request NOW for localZipCode: {}", localZipCode);
    LocationEntity locationEntity = locationJpaRepository.findById(localZipCode)
        .orElseThrow(() -> new LocationNotSupportedException(localZipCode));
    WeatherEntity weatherEntityData = weatherService.getNow(locationEntity);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(locationEntity, Collections.singletonList(weatherEntityData));
    return ResponseEntity.ok(weatherResponse);
  }

  @GetMapping("/24h")
  public ResponseEntity<WeatherResponse> getLast24Hours(@PathVariable String localZipCode) {
    log.info("Received Weather request 24h for localZipCode: {}", localZipCode);
    LocationEntity locationEntity = locationJpaRepository.findById(localZipCode)
        .orElseThrow(() -> new LocationNotSupportedException(localZipCode));
    List<WeatherEntity> weatherEntityData = weatherService.getLast24h(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(locationEntity, weatherEntityData);
    return ResponseEntity.ok(weatherResponse);

  }

  @GetMapping("/3d")
  public ResponseEntity<WeatherResponse> getLast3Days(@PathVariable String localZipCode) {
    log.info("Received Weather request 3d for localZipCode: {}", localZipCode);
    LocationEntity locationEntity = locationJpaRepository.findById(localZipCode)
        .orElseThrow(() -> new LocationNotSupportedException(localZipCode));
    List<WeatherEntity> weatherEntityData = weatherService.getLast3Days(localZipCode);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(locationEntity, weatherEntityData);
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
    LocationEntity locationEntity = locationJpaRepository.findById(localZipCode)
        .orElseThrow(() -> new LocationNotSupportedException(localZipCode));
    Instant instant = ControllerUtil.atStartOfDay(date, locationEntity);
    List<WeatherEntity> weatherEntityData = weatherService.getSpecificDate(localZipCode, instant);
    WeatherResponse weatherResponse = weatherDtoMapper.mapTo(locationEntity, weatherEntityData);
    return ResponseEntity.ok(weatherResponse);
  }


}
