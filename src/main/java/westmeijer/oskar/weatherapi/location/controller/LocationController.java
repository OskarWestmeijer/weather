package westmeijer.oskar.weatherapi.location.controller;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import westmeijer.oskar.weatherapi.location.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.LocationsApi;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationDto;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationResponse;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class LocationController implements LocationsApi {

  private final LocationService locationService;

  private final LocationDtoMapper locationDtoMapper;

  @Override
  public ResponseEntity<LocationResponse> getLocations() {
    List<Location> locations = locationService.getAllOmitWeather();
    LocationResponse response = locationDtoMapper.mapToLocationResponse(locations);
    return ResponseEntity.ok(response);
  }

}
