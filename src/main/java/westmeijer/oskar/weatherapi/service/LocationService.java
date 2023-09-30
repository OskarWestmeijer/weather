package westmeijer.oskar.weatherapi.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.model.Location;

@Service
@RequiredArgsConstructor
public class LocationService {

  private final LocationRepository locationRepository;

  public Location findById(String localZipCode) {
    Objects.requireNonNull(localZipCode, "localZipCode must not be null");
    return locationRepository.findByLocalZipCode(localZipCode);
  }

  public List<Location> getAll() {
    return locationRepository.getAll();
  }

  public Location getNextImportLocation() {
    return locationRepository.getNextImportLocation();
  }

  public Location saveAndFlush(Location location) {
    Objects.requireNonNull(location, "location must not be null");
    return locationRepository.saveAndFlush(location);
  }
}
