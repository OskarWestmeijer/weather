package westmeijer.oskar.weatherapi.location.repository;

import static java.util.Objects.requireNonNull;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Component
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

  private final LocationJpaRepository locationJpaRepository;

  private final LocationEntityMapper locationEntityMapper;

  @Override
  public List<Location> getAllOmitWeather() {
    List<LocationEntity> locationEntities = locationJpaRepository.findAll();
    return locationEntityMapper.mapToLocationListWithoutWeather(locationEntities);
  }

  @Override
  public Location getNextImportLocation() {
    LocationEntity location = locationJpaRepository.getNextImportLocation();
    return locationEntityMapper.mapToLocationWithoutWeather(location);
  }

  @Override
  public List<Location> getAllWithLatest() {
    List<LocationEntity> locationEntities = locationJpaRepository.getAllWithLatest();
    return locationEntityMapper.mapToLocationList(locationEntities);
  }

  @Override
  public Location getByIdOmitWeather(Integer locationId) {
    requireNonNull(locationId, "locationId is required");
    LocationEntity locationEntity = locationJpaRepository.getById(locationId)
        .orElseThrow(() -> new LocationNotSupportedException(locationId));
    return locationEntityMapper.mapToLocationWithoutWeather(locationEntity);
  }


}
