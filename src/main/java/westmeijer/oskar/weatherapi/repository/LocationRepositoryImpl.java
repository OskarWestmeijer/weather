package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.mapper.LocationMapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;

@Component
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

  private final LocationJpaRepository locationJpaRepository;

  private final LocationMapper locationMapper;

  @Override
  public List<Location> getAll() {
    List<LocationEntity> locationEntities = locationJpaRepository.findAll();
    return locationMapper.mapList(locationEntities);
  }
}
