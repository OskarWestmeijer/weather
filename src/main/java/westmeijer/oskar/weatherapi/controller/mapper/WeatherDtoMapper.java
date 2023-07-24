package westmeijer.oskar.weatherapi.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.controller.model.WeatherDto;
import westmeijer.oskar.weatherapi.controller.model.WeatherResponse;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherDtoMapper {

  @Mapping(source = "location", target = "location")
  @Mapping(source = "weatherList", target = "weatherData")
  @Mapping(target = "responseTime", expression = "java(Instant.now())")
  WeatherResponse mapTo(Location location, List<Weather> weatherList);

  WeatherDto mapTo(Weather weather);

}
