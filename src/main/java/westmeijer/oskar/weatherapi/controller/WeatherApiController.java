package westmeijer.oskar.weatherapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.repository.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherApiService;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.controller.model.WeatherMapper;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{zipCode}")
@RequiredArgsConstructor
@Slf4j
public class WeatherApiController {

    private final WeatherApiService weatherApiService;

    private final LocationRepository locationRepository;

    @GetMapping("/now")
    public ResponseEntity<WeatherResponse> getNow(@PathVariable int zipCode) {
        log.info("Received Weather request NOW for zipCode: {}", zipCode);
        Location location = locationRepository.findById(zipCode).orElseThrow(() -> new LocationNotSupportedException(zipCode));
        Weather weatherData = weatherApiService.getNow(location);
        WeatherResponse weatherResponse = WeatherMapper.map(location, Collections.singletonList(weatherData));
        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping("/24h")
    public ResponseEntity<WeatherResponse> getLast24Hours(@PathVariable int zipCode) {
        log.info("Received Weather request 24h for zipCode: {}", zipCode);
        Location location = locationRepository.findById(zipCode).orElseThrow(() -> new LocationNotSupportedException(zipCode));
        List<Weather> weatherData = weatherApiService.getLast24h(zipCode);
        WeatherResponse weatherResponse = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherResponse);

    }

    @GetMapping("/3d")
    public ResponseEntity<WeatherResponse> getLast3Days(@PathVariable int zipCode) {
        log.info("Received Weather request 3d for zipCode: {}", zipCode);
        Location location = locationRepository.findById(zipCode).orElseThrow(() -> new LocationNotSupportedException(zipCode));
        List<Weather> weatherData = weatherApiService.getLast3Days(zipCode);
        WeatherResponse weatherResponse = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherResponse);
    }

    /**
     * Get weather for a certain date and location.
     *
     * @param zipCode - location to be requested
     * @param date    - expected format: dd-MM-YYYY
     * @return
     */
    @GetMapping("/{date}")
    public ResponseEntity<WeatherResponse> getSpecificDate(@PathVariable int zipCode, @PathVariable String date) {
        log.info("Received Weather request SPECIFIC date for zipCode: {}, date: {}", zipCode, date);
        Location location = locationRepository.findById(zipCode).orElseThrow(() -> new LocationNotSupportedException(zipCode));
        Instant instant = ControllerUtil.atStartOfDay(date, location);
        List<Weather> weatherData = weatherApiService.getSpecificDate(zipCode, instant);
        WeatherResponse weatherResponse = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherResponse);
    }


}
