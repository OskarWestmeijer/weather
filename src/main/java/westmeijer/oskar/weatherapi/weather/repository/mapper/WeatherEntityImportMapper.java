package westmeijer.oskar.weatherapi.weather.repository.mapper;

import static java.util.function.Function.identity;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface WeatherEntityImportMapper {


  @Named("mapToLocationEntity")
  @Mapping(target = "id", source = "locationId")
  @Mapping(target = "weather", ignore = true)
  @Mapping(target = "modifiedAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  LocationEntity mapToLocationEntity(Location location);

  @Named("mapToWeatherEntity")
  @Mapping(target = "location", ignore = true)
  // createdAt and modifiedAt are controlled by the database
  @Mapping(target = "modifiedAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  WeatherEntity mapToWeatherEntity(Weather weather);

  @AfterMapping
  default void bindWeatherToLocation(@MappingTarget WeatherEntity weatherEntity, Weather weather) {
    LocationEntity locationEntity = mapToLocationEntity(weather.getLocation());
    locationEntity.addWeather(weatherEntity);
  }

}
