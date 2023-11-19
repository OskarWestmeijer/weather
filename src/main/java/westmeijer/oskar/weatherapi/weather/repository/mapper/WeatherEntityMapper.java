package westmeijer.oskar.weatherapi.weather.repository.mapper;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring", uses = LocationEntityMapper.class)
public interface WeatherEntityMapper {

  @Mapping(target = "location", ignore = true)
  Weather map(WeatherEntity weatherEntity);

  List<Weather> mapList(List<WeatherEntity> weatherEntity);

}
