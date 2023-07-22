package westmeijer.oskar.weatherapi.util;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import westmeijer.oskar.weatherapi.controller.mapper.LocationDtoMapper;
import westmeijer.oskar.weatherapi.controller.mapper.WeatherDtoMapper;

@TestConfiguration
public class WebMvcTestConfig {

  @Bean
  public WeatherDtoMapper weatherDtoMapper() {
    return Mappers.getMapper(WeatherDtoMapper.class);
  }

  @Bean
  public LocationDtoMapper locationDtoMapper() {
    return Mappers.getMapper(LocationDtoMapper.class);
  }

}
