package westmeijer.oskar.weatherapi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.business.WeatherService;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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

    @GetMapping("/api/weather/{zipCode}")
    public ResponseEntity<?> getWeather(@PathVariable int zipCode) {

        logger.info("Received Weather request for zip code: {}", zipCode);

        if (zipCode == ZIP_CODE_LUEBECK) {
            List<Weather> weatherData = weatherService.getWeather();
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
