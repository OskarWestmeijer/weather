package westmeijer.oskar.weatherapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import westmeijer.oskar.weatherapi.model.WeatherDTOBuilder;
import westmeijer.oskar.weatherapi.model.WeatherEntity;
import westmeijer.oskar.weatherapi.model.WeatherDTO;
import westmeijer.oskar.weatherapi.service.WeatherService;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity getWeather(@PathVariable int zipCode) {

        logger.info("Received Weather request for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<WeatherEntity> weatherEntities = weatherService.getWeather();

            List<WeatherDTO> weatherList = weatherEntities.stream()
                    .map(weatherEntity -> new WeatherDTOBuilder().setId(weatherEntity.getId())
                            .setTemperature(weatherEntity.getTemperature()).createWeatherDTO())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(weatherList);
        } else {
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

    @PostMapping("/api/weather/refresh")
    public ResponseEntity refreshWeather() {
        return ResponseEntity.ok("Success!");
    }

}
