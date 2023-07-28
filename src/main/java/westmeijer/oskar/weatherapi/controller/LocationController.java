package westmeijer.oskar.weatherapi.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.openapi.api.LocationsApi;
import westmeijer.oskar.openapi.model.LocationDto;
import westmeijer.oskar.weatherapi.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.service.LocationService;
import westmeijer.oskar.weatherapi.service.model.Location;

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LocationController implements LocationsApi {

  private final LocationService locationService;

  private final LocationDtoMapper locationDtoMapper;

  @Override
  public ResponseEntity<List<LocationDto>> getLocations() {
    List<Location> locations = locationService.getAll();
    List<LocationDto> locationDtos = locationDtoMapper.mapList(locations);
    return ResponseEntity.ok(locationDtos);
  }

}
