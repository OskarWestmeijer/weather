package westmeijer.oskar.weatherapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.controller.model.WeatherMapper;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.repository.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherApiService;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{localZipCode}")
@RequiredArgsConstructor
@Slf4j
public class WeatherApiController {

    private final WeatherApiService weatherApiService;

    private final LocationRepository locationRepository;


    @GetMapping("/now")
    public ResponseEntity<WeatherResponse> getNow(@PathVariable int localZipCode) {
        log.info("Received Weather request NOW for localZipCode: {}", localZipCode);
        Location location = locationRepository.findById(localZipCode).orElseThrow(() -> new LocationNotSupportedException(localZipCode));
        Weather weatherData = weatherApiService.getNow(location);
        WeatherResponse weatherResponse = WeatherMapper.INSTANCE.mapTo(location, Collections.singletonList(weatherData));
        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping("/24h")
    public ResponseEntity<WeatherResponse> getLast24Hours(@PathVariable int localZipCode) {
        log.info("Received Weather request 24h for localZipCode: {}", localZipCode);
        Location location = locationRepository.findById(localZipCode).orElseThrow(() -> new LocationNotSupportedException(localZipCode));
        List<Weather> weatherData = weatherApiService.getLast24h(localZipCode);
        WeatherResponse weatherResponse = WeatherMapper.INSTANCE.mapTo(location, weatherData);
        return ResponseEntity.ok(weatherResponse);

    }

    @GetMapping("/3d")
    public ResponseEntity<WeatherResponse> getLast3Days(@PathVariable int localZipCode) {
        log.info("Received Weather request 3d for localZipCode: {}", localZipCode);
        Location location = locationRepository.findById(localZipCode).orElseThrow(() -> new LocationNotSupportedException(localZipCode));
        List<Weather> weatherData = weatherApiService.getLast3Days(localZipCode);
        WeatherResponse weatherResponse = WeatherMapper.INSTANCE.mapTo(location, weatherData);
        return ResponseEntity.ok(weatherResponse);
    }

    /**
     * Get weather for a certain date and location.
     *
     * @param localZipCode - location to be requested
     * @param date    - expected format: dd-MM-YYYY
     * @return
     */
    @GetMapping("/{date}")
    public ResponseEntity<WeatherResponse> getSpecificDate(@PathVariable int localZipCode, @PathVariable String date) {
        log.info("Received Weather request SPECIFIC date for localZipCode: {}, date: {}", localZipCode, date);
        Location location = locationRepository.findById(localZipCode).orElseThrow(() -> new LocationNotSupportedException(localZipCode));
        Instant instant = ControllerUtil.atStartOfDay(date, location);
        List<Weather> weatherData = weatherApiService.getSpecificDate(localZipCode, instant);
        WeatherResponse weatherResponse = WeatherMapper.INSTANCE.mapTo(location, weatherData);
        return ResponseEntity.ok(weatherResponse);
    }


}
