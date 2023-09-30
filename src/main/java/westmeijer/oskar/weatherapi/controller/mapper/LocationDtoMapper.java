package westmeijer.oskar.weatherapi.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.openapi.server.model.LocationDto;
import westmeijer.oskar.weatherapi.service.model.Location;

@Mapper(componentModel = "spring")
public interface LocationDtoMapper {

  List<LocationDto> mapList(List<Location> location);

  @Mapping(source = "openWeatherApiLocationCode", target = "locationCode")
  LocationDto map(Location location);

}
