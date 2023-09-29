package westmeijer.oskar.weatherapi.openweatherapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Mapper(componentModel = "spring")
public interface OpenWeatherApiMapper {

  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "temperature", source = "response.main.temp")
  @Mapping(target = "humidity", source = "response.main.humidity")
  @Mapping(target = "windSpeed", source = "response.wind.speed")
  @Mapping(target = "recordedAt", expression = "java(Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  Weather map(GeneratedOpenWeatherApiResponse response, String localZipCode);

}
