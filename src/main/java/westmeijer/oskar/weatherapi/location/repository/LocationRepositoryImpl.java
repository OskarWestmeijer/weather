package westmeijer.oskar.weatherapi.location.repository;

import static java.util.Objects.requireNonNull;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityImportMapper;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

  private final LocationJpaRepository locationJpaRepository;

  private final LocationEntityMapper locationEntityMapper;

  private final LocationEntityImportMapper locationEntityImportMapper;

  @Override
  public List<Location> getAllOmitWeather() {
    List<LocationEntity> locationEntities = locationJpaRepository.findAll();
    return locationEntityMapper.mapToLocationListWithEmptyWeather(locationEntities);
  }

  @Override
  public Location getNextImportLocation() {
    LocationEntity location = locationJpaRepository.getNextImportLocation();
    return locationEntityMapper.mapToLocationWithEmptyWeather(location);
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
    return locationEntityMapper.mapToLocationWithEmptyWeather(locationEntity);
  }

  @Override
  public Location save(Location location) {
    requireNonNull(location, "location is required");
    var locationEntity = locationEntityImportMapper.mapToLocationEntity(location);
    return locationEntityMapper.mapToLocation(locationJpaRepository.saveAndFlush(locationEntity));
  }

}
