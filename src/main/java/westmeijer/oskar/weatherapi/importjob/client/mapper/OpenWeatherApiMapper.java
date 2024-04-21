package westmeijer.oskar.weatherapi.importjob.client.mapper;

import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface OpenWeatherApiMapper {

  @Mapping(target = "lastImportAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  @Mapping(target = "weather", source = "response", qualifiedByName = "mapWeatherResponse")
  Location mapToLocation(GeneratedOpenWeatherApiResponse response, Location location);

  @Named("mapWeatherResponse")
  default List<Weather> mapWeatherResponse(GeneratedOpenWeatherApiResponse response) {
    return Collections.singletonList(mapToWeather(response));
  }

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "temperature", source = "response.main.temp")
  @Mapping(target = "humidity", source = "response.main.humidity")
  @Mapping(target = "windSpeed", source = "response.wind.speed")
  @Mapping(target = "recordedAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  Weather mapToWeather(GeneratedOpenWeatherApiResponse response);

}
