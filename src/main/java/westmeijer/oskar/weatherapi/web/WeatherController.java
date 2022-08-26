package westmeijer.oskar.weatherapi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.business.WeatherService;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    private static final int ZIP_CODE_LUEBECK = 23552;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/ping")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping({"/api/weather/{zipCode}", "/api/weather/{zipCode}/24h"})
    public ResponseEntity<?> getWeather(@PathVariable int zipCode) {

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

    @GetMapping("/api/weather/{zipCode}/3d")
    public ResponseEntity<?> getWeatherLastThreeDays(@PathVariable int zipCode) {

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

    @GetMapping("/api/weather/{zipCode}/{date}")
    public ResponseEntity<?> getWeatherSpecificDate(@PathVariable int zipCode, @PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.from(formatter.parse(date));
        Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);

        logger.info("Received Weather request SPECIFIC date for zip code: {}, date: {}", zipCode, instant);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherService.getSpecificWeather(instant);
            WeatherDTO weatherDTO = WeatherMapper.map(String.valueOf(zipCode), weatherData);
            return ResponseEntity.ok(weatherDTO);
        } else {
            logger.info("Unknown zip code! Bad request.");
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

    @GetMapping("/api/memory")
    public ResponseEntity<Map<String, Long>> getMemoryStatistics() {
        long byteToLong = 1000000;
        Map<String, Long> mem = new HashMap<>();
        mem.put("TotalMem", Runtime.getRuntime().totalMemory() / byteToLong);
        mem.put("MaxMem", Runtime.getRuntime().maxMemory() / byteToLong);
        mem.put("FreeMem", Runtime.getRuntime().freeMemory() / byteToLong);
        logger.info("Memory: {}", mem.toString());
        return ResponseEntity.ok(mem);
    }


}
