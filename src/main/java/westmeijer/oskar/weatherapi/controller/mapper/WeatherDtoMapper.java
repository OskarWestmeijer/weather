package westmeijer.oskar.weatherapi.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.openapi.server.model.WeatherDto;
import westmeijer.oskar.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherDtoMapper {

  @Mapping(source = "location.cityName", target = "cityName")
  @Mapping(source = "location.localZipCode", target = "localZipCode")
  @Mapping(source = "location.country", target = "country")
  @Mapping(source = "weatherList", target = "weatherData")
  WeatherResponse mapTo(Location location, List<Weather> weatherList);

  WeatherDto mapTo(Weather weather);

}
