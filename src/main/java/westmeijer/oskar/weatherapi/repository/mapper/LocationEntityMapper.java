package westmeijer.oskar.weatherapi.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

  List<Location> mapList(List<LocationEntity> locationEntityList);

  Location map(LocationEntity locationEntity);

  @Mapping(target = "modifiedAt", expression = "java(Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  LocationEntity map(Location location);

}
