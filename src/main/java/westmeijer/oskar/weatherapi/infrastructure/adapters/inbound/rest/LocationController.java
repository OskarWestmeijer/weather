package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import westmeijer.oskar.weatherapi.application.ports.inbound.GetLocationUseCase;
import westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.mappers.LocationDtoMapper;
import westmeijer.oskar.weatherapi.application.services.LocationService;
import westmeijer.oskar.weatherapi.domain.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.api.LocationsApi;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationResponse;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class LocationController implements LocationsApi {

  private final GetLocationUseCase getLocationUseCase;

  private final LocationDtoMapper locationDtoMapper;

  @Override
  public ResponseEntity<LocationResponse> getLocations() {
    List<Location> locations = getLocationUseCase.getAllOmitWeather();
    LocationResponse response = locationDtoMapper.mapToLocationResponse(locations);
    return ResponseEntity.ok(response);
  }

}
