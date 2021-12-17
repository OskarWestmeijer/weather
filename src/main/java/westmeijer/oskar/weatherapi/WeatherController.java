package westmeijer.oskar.weatherapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

    private static final int ZIP_CODE_LUEBECK = 23552;

    @GetMapping("/api/weather/{zipCode}")
    public ResponseEntity<String> getWeather(@PathVariable int zipCode) {

        if (zipCode == ZIP_CODE_LUEBECK) {
            return ResponseEntity.ok("The sun is shining in Lübeck!");
        } else {
            return ResponseEntity.badRequest().body("Unknown zip code!");
        }
    }

}
