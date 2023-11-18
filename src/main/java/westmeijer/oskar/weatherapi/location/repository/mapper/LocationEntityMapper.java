package westmeijer.oskar.weatherapi.location.repository.mapper;

import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapper;

@Mapper(componentModel = "spring", uses = WeatherEntityMapper.class, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface LocationEntityMapper {

  @Named("mapToLocationListWithoutWeather")
  @IterableMapping(qualifiedByName = "mapToLocationWithoutWeather")
  List<Location> mapToLocationListWithoutWeather(List<LocationEntity> locationEntityList);

  @Named("mapToLocationWithoutWeather")
  @Mapping(target = "locationId", source = "id")
  @Mapping(target = "weather", ignore = true)
  Location mapToLocationWithoutWeather(LocationEntity locationEntity);


  @Named("mapToLocationList")
  @IterableMapping(qualifiedByName = "mapToLocation")
  List<Location> mapToLocationList(List<LocationEntity> locationEntityList);

  @Named("mapToLocation")
  @Mapping(target = "locationId", source = "id")
  Location mapToLocation(LocationEntity locationEntity);

}
