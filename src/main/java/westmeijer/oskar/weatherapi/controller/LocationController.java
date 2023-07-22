package westmeijer.oskar.weatherapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.repository.model.Location;
import westmeijer.oskar.weatherapi.repository.jpa.LocationRepository;

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository locationRepository;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

}
