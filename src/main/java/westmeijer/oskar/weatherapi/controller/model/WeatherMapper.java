package westmeijer.oskar.weatherapi.controller.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.util.List;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper( WeatherMapper.class );

    @Mapping(source = "location", target = "location")
    @Mapping(source = "weather", target = "weatherData")
    @Mapping(target = "responseTime", expression = "java(Instant.now())")
    WeatherResponse mapTo(Location location, List<Weather> weather);

}
