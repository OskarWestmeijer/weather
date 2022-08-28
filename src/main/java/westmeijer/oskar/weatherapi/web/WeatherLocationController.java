package westmeijer.oskar.weatherapi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.business.WeatherService;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{zipCode}")
public class WeatherLocationController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherLocationController.class);

    private final WeatherService weatherService;

    private static final int ZIP_CODE_LUEBECK = 23552;

    public WeatherLocationController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping({"/", "/24h"})
    public ResponseEntity<?> getLast24Hours(@PathVariable int zipCode) {

        logger.info("Received Weather request 24h for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherService.getLatestWeather();
            WeatherDTO weatherDTO = WeatherMapper.map(String.valueOf(zipCode), weatherData);
            return ResponseEntity.ok(weatherDTO);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

    @GetMapping("/3d")
    public ResponseEntity<?> getLast3Days(@PathVariable int zipCode) {

        logger.info("Received Weather request 3d for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherService.getWeatherLastThreeDays();
            WeatherDTO weatherDTO = WeatherMapper.map(String.valueOf(zipCode), weatherData);
            return ResponseEntity.ok(weatherDTO);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> getSpecificDate(@PathVariable int zipCode, @PathVariable String date) {
        Instant instant = ControllerUtil.parse(date);

        logger.info("Received Weather request SPECIFIC date for zip code: {}, instant: {}", zipCode, instant);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherService.getSpecificWeather(instant);
            WeatherDTO weatherDTO = WeatherMapper.map(String.valueOf(zipCode), weatherData);
            return ResponseEntity.ok(weatherDTO);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }


}
