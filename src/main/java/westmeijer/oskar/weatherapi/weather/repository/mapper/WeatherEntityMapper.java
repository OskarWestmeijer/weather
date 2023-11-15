package westmeijer.oskar.weatherapi.weather.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherEntityMapper {

  Weather map(WeatherEntity weatherEntity);

  @Mapping(target = "locationId", source = "id")
  Location map(LocationEntity locationEntity);

  List<Weather> mapList(List<WeatherEntity> weatherEntity);

  @Mapping(target = "modifiedAt", expression = "java(Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  WeatherEntity map(Weather weather);

}
