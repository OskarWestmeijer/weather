package westmeijer.oskar.weatherapi.location.service;

import static java.util.Objects.requireNonNull;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.location.repository.LocationRepository;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Service
@RequiredArgsConstructor
public class LocationService {

  private final LocationRepository locationRepository;

  public Location getById(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    return locationRepository.getById(locationId);
  }

  public List<Location> getAll() {
    List<Location> locations = locationRepository.getAll();
    return locations;
  }

  public Location getNextImportLocation() {
    return locationRepository.getNextImportLocation();
  }

  public void updateLastImportAt(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    locationRepository.updateLastImportAt(locationId);
  }

}
