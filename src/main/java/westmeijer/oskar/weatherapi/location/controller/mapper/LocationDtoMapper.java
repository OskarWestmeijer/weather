package westmeijer.oskar.weatherapi.location.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationDto;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationResponse;
import westmeijer.oskar.weatherapi.weather.controller.mapper.WeatherDtoMapper;

@Mapper(componentModel = "spring", uses = WeatherDtoMapper.class)
public interface LocationDtoMapper {

  default LocationResponse mapToLocationResponse(List<Location> locations) {
    return new LocationResponse(mapList(locations));
  }

  List<LocationDto> mapList(List<Location> location);

  LocationDto map(Location location);

}
