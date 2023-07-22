package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
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
}
