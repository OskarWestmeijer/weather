package westmeijer.oskar.weatherapi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.repository.database.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherApiService;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.web.ControllerUtil;
import westmeijer.oskar.weatherapi.web.WeatherDTO;
import westmeijer.oskar.weatherapi.web.WeatherMapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{zipCode}")
public class WeatherApiController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiController.class);

    private final WeatherApiService weatherApiService;

    private final LocationRepository locationRepository;

    public WeatherApiController(WeatherApiService weatherApiService, LocationRepository locationRepository) {
        this.weatherApiService = weatherApiService;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/24h")
    public ResponseEntity<WeatherDTO> getLast24Hours(@PathVariable int zipCode) {
        logger.info("Received Weather request 24h for zip code: {}", zipCode);
        Location location = locationRepository.findById(zipCode).orElseThrow();
        List<Weather> weatherData = weatherApiService.getLast24h(zipCode);
        WeatherDTO weatherDTO = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherDTO);

    }

    @GetMapping("/3d")
    public ResponseEntity<WeatherDTO> getLast3Days(@PathVariable int zipCode) {
        logger.info("Received Weather request 3d for zip code: {}", zipCode);
        Location location = locationRepository.findById(zipCode).orElseThrow();
        List<Weather> weatherData = weatherApiService.getLast3Days(zipCode);
        WeatherDTO weatherDTO = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherDTO);
    }

    /**
     * Get weather for a certain date.
     *
     * @param zipCode
     * @param date    format: dd-MM-YYYY
     * @return
     */
    @GetMapping("/{date}")
    public ResponseEntity<WeatherDTO> getSpecificDate(@PathVariable int zipCode, @PathVariable String date) {
        logger.info("Received Weather request SPECIFIC date for zip code: {}, date: {}", zipCode, date);
        Location location = locationRepository.findById(zipCode).orElseThrow();
        Instant instant = ControllerUtil.atStartOfDay(date, location);
        List<Weather> weatherData = weatherApiService.getSpecificDate(zipCode, instant);
        WeatherDTO weatherDTO = WeatherMapper.map(location, weatherData);
        return ResponseEntity.ok(weatherDTO);
    }


}
