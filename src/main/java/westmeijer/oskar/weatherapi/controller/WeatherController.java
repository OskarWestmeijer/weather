package westmeijer.oskar.weatherapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.service.WeatherService;

@Controller
public class WeatherController {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private WeatherService weatherService;

    private static final int ZIP_CODE_LUEBECK = 23552;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather/{zipCode}")
    public ResponseEntity getWeather(@PathVariable int zipCode) {

        logger.info("Received Weather request for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {

            return ResponseEntity.ok(weatherService.getWeather());
        } else {
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

}
