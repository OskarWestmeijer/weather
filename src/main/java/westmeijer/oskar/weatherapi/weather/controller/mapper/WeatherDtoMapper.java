package westmeijer.oskar.weatherapi.weather.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.location.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.PagingDetails;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage;

@Mapper(componentModel = "spring", uses = LocationDtoMapper.class)
public interface WeatherDtoMapper {

  @Mapping(source = "location.locationId", target = "locationId")
  @Mapping(source = "location.cityName", target = "cityName")
  @Mapping(source = "location.country", target = "country")
  @Mapping(source = "weatherFeedPage.weatherList", target = "weatherData")
  WeatherResponse mapTo(Location location, WeatherFeedPage weatherFeedPage);

  @Mapping(source = "pageRecords", target = "pageRecordsCount")
  @Mapping(source = "totalRecords", target = "totalRecordsCount")
  PagingDetails mapTo(WeatherFeedPage.PagingDetails pagingDetails);

  WeatherDto mapTo(Weather weather);

}
