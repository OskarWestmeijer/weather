package westmeijer.oskar.weatherapi.location.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

  List<Location> mapList(List<LocationEntity> locationEntityList);

  @Mapping(target = "locationId", source = "id")
  Location map(LocationEntity locationEntity);

  @Mapping(target = "id", source = "locationId")
  @Mapping(target = "modifiedAt", expression = "java(Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  LocationEntity map(Location location);

}
