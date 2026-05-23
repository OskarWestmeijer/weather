package westmeijer.oskar.weatherapi.infrastructure.adapters.outbound.persistence.mappers;

import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.infrastructure.adapters.outbound.persistence.model.LocationEntity;
import westmeijer.oskar.weatherapi.domain.model.Location;

@Mapper(componentModel = "spring", uses = WeatherEntityMapper.class, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface LocationEntityMapper {

  @Named("mapToLocationListWithEmptyWeather")
  @IterableMapping(qualifiedByName = "mapToLocationWithEmptyWeather")
  List<Location> mapToLocationListWithEmptyWeather(List<LocationEntity> locationEntityList);

  @Named("mapToLocationWithEmptyWeather")
  @Mapping(target = "locationId", source = "id")
  @Mapping(target = "weather", expression = "java(java.util.Collections.emptyList())")
  Location mapToLocationWithEmptyWeather(LocationEntity locationEntity);

  @Named("mapToLocationList")
  @IterableMapping(qualifiedByName = "mapToLocation")
  List<Location> mapToLocationList(List<LocationEntity> locationEntityList);

  @Named("mapToLocation")
  @Mapping(target = "locationId", source = "id")
  Location mapToLocation(LocationEntity locationEntity);

}
