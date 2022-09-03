package westmeijer.oskar.weatherapi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.service.WeatherApiService;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.web.ControllerUtil;
import westmeijer.oskar.weatherapi.web.WeatherDTO;
import westmeijer.oskar.weatherapi.web.WeatherMapper;

import java.time.Instant;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/weather/{zipCode}")
public class WeatherApiController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiController.class);

    private final WeatherApiService weatherApiService;

    private static final int ZIP_CODE_LUEBECK = 23552;

    public WeatherApiController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/24h")
    public ResponseEntity<?> getLast24Hours(@PathVariable int zipCode) {

        logger.info("Received Weather request 24h for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherApiService.getLast24h();
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
            List<Weather> weatherData = weatherApiService.getLast3Days();
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
            List<Weather> weatherData = weatherApiService.getSpecificDate(instant);
            WeatherDTO weatherDTO = WeatherMapper.map(String.valueOf(zipCode), weatherData);
            return ResponseEntity.ok(weatherDTO);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }


}
