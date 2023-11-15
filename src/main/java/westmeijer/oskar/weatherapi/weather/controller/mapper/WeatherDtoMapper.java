package westmeijer.oskar.weatherapi.weather.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherDtoMapper {

  @Mapping(source = "location.locationId", target = "locationId")
  @Mapping(source = "location.cityName", target = "cityName")
  @Mapping(source = "location.country", target = "country")
  @Mapping(source = "weatherList", target = "weatherData")
  WeatherResponse mapTo(Location location, List<Weather> weatherList);

  WeatherDto mapTo(Weather weather);

}
