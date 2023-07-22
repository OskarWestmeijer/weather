package westmeijer.oskar.weatherapi.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.controller.model.LocationDto;
import westmeijer.oskar.weatherapi.service.model.Location;

@Mapper(componentModel = "spring")
public interface LocationDtoMapper {

  List<LocationDto> mapList(List<Location> location);

  LocationDto map(Location location);

}
