package westmeijer.oskar.weatherapi.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.controller.model.WeatherDto;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherDtoMapper {

    @Mapping(source = "locationEntity", target = "locationEntity")
    @Mapping(source = "weatherEntities", target = "weatherData")
    @Mapping(target = "responseTime", expression = "java(Instant.now())")
    WeatherResponse mapTo(LocationEntity locationEntity, List<WeatherEntity> weatherEntities);

    WeatherDto mapTo(WeatherEntity weatherEntity);

}
