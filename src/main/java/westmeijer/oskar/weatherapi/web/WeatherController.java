package westmeijer.oskar.weatherapi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.web.model.WeatherDTO;
import westmeijer.oskar.weatherapi.service.WeatherService;

import java.util.List;

@Controller
public class WeatherController {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    private static final int ZIP_CODE_LUEBECK = 23552;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/ping")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/api/weather/{zipCode}")
    public ResponseEntity<?> getWeather(@PathVariable int zipCode) {

        logger.info("Received Weather request for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<WeatherDTO> weatherList = weatherService.getWeather();
            return ResponseEntity.ok(weatherList);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }



}
