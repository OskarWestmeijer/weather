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

  public Location getByIdOmitWeather(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    return locationRepository.getByIdOmitWeather(locationId);
  }

  public List<Location> getAllWithLatest() {
    return locationRepository.getAllWithLatest();
  }

  public List<Location> getAllOmitWeather() {
    return locationRepository.getAllOmitWeather();
  }

  public Location getNextImportLocation() {
    return locationRepository.getNextImportLocation();
  }

}
