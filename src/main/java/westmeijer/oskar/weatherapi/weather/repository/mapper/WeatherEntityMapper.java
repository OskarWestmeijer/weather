package westmeijer.oskar.weatherapi.weather.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherEntityMapper {

  Weather map(WeatherEntity weatherEntity);

  List<Weather> mapList(List<WeatherEntity> weatherEntity);

  @Mapping(target = "modifiedAt", expression = "java(Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  WeatherEntity map(Weather weather);

}
