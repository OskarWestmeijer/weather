package westmeijer.oskar.weatherapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LocationController {

    private final LocationJpaRepository locationJpaRepository;

    @GetMapping("/locations")
    public ResponseEntity<List<LocationEntity>> getLocations() {
        return ResponseEntity.ok(locationJpaRepository.findAll());
    }

}
