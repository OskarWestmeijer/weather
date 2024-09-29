package westmeijer.oskar.weatherapi.weather.controller.mapper;

import java.time.Instant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.location.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.PagingDetails;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring", uses = LocationDtoMapper.class)
public interface WeatherDtoMapper {

  @Mapping(source = "location.locationId", target = "locationId")
  @Mapping(source = "location.cityName", target = "cityName")
  @Mapping(source = "location.country", target = "country")
  @Mapping(source = "weatherList", target = "weatherData")
  WeatherResponse mapTo(Location location, List<Weather> weatherList, PagingDetails pagingDetails);

  @Mapping(source = "pageRecordsCount", target = "pageRecordsCount")
  @Mapping(source = "totalRecordsCount", target = "totalRecordsCount")
  @Mapping(source = "hasNewerRecords", target = "hasNewerRecords")
  @Mapping(source = "nextFrom", target = "nextFrom")
  @Mapping(source = "nextLink", target = "nextLink")
  PagingDetails mapTo(Integer pageRecordsCount, Integer totalRecordsCount, Boolean hasNewerRecords, Instant nextFrom, String nextLink);

  WeatherDto mapTo(Weather weather);

}
