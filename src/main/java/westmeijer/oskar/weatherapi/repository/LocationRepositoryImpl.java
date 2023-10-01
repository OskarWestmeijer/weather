package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.controller.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.service.model.Location;

@Component
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

  private final LocationJpaRepository locationJpaRepository;

  private final LocationEntityMapper locationEntityMapper;

  @Override
  public List<Location> getAll() {
    List<LocationEntity> locationEntities = locationJpaRepository.findAll();
    return locationEntityMapper.mapList(locationEntities);
  }

  @Override
  public ImportJobLocation getNextImportLocation() {
    LocationEntity location = locationJpaRepository.findFirstByOrderByLastImportAtAsc();
    return locationEntityMapper.mapToJobLocation(location);
  }

  public void updateLastImportAt(ImportJobLocation location) {
    Objects.requireNonNull(location, "location must not be null");
    locationJpaRepository.updateLastImportAt(location.id());
  }

  @Override
  public Location findByLocalZipCode(String localZipCode) {
    Objects.requireNonNull(localZipCode, "localZipCode must not be null");
    LocationEntity locationEntity = locationJpaRepository.findByLocalZipCode(localZipCode)
        .orElseThrow(() -> new LocationNotSupportedException(localZipCode));
    return locationEntityMapper.map(locationEntity);
  }
}
