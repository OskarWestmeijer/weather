package westmeijer.oskar.weatherapi.repository.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Mapper(componentModel = "spring")
public interface WeatherEntityMapper {

  Weather map(WeatherEntity weatherEntity);

  List<Weather> mapList(List<WeatherEntity> weatherEntity);

  WeatherEntity map(Weather weather);

}
