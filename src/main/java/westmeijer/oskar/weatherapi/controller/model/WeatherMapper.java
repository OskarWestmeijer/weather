package westmeijer.oskar.weatherapi.controller.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.model.Weather;

import java.util.List;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mapping(source = "locationEntity", target = "locationEntity")
    @Mapping(source = "weather", target = "weatherData")
    @Mapping(target = "responseTime", expression = "java(Instant.now())")
    WeatherResponse mapTo(LocationEntity locationEntity, List<Weather> weather);

    WeatherDto mapTo(Weather weather);

}
