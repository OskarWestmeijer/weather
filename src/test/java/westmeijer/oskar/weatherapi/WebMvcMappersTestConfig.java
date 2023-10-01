package westmeijer.oskar.weatherapi;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import westmeijer.oskar.weatherapi.location.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.weather.controller.mapper.WeatherDtoMapper;
import westmeijer.oskar.weatherapi.importjob.client.mapper.OpenWeatherApiMapper;

@TestConfiguration
public class WebMvcMappersTestConfig {

  @Bean
  public WeatherDtoMapper weatherDtoMapper() {
    return Mappers.getMapper(WeatherDtoMapper.class);
  }

  @Bean
  public LocationDtoMapper locationDtoMapper() {
    return Mappers.getMapper(LocationDtoMapper.class);
  }

  @Bean
  public OpenWeatherApiMapper openWeatherApiMapper() {
    return Mappers.getMapper(OpenWeatherApiMapper.class);
  }

}
