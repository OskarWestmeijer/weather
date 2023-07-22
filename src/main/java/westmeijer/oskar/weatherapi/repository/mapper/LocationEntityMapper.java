package westmeijer.oskar.weatherapi.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;

@Mapper(componentModel = "spring")
public interface LocationEntityMapper {

  List<Location> mapList(List<LocationEntity> locationEntityList);

  Location map(LocationEntity locationEntity);

}
