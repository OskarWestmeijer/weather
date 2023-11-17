package westmeijer.oskar.weatherapi.importjob.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface OpenWeatherApiMapper {

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "temperature", source = "response.main.temp")
  @Mapping(target = "humidity", source = "response.main.humidity")
  @Mapping(target = "windSpeed", source = "response.wind.speed")
  @Mapping(target = "recordedAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  Weather map(GeneratedOpenWeatherApiResponse response, Location location);

}
