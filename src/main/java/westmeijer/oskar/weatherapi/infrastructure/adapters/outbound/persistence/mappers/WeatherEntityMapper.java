package westmeijer.oskar.weatherapi.infrastructure.adapters.outbound.persistence.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.infrastructure.adapters.outbound.persistence.model.WeatherEntity;
import westmeijer.oskar.weatherapi.domain.model.Weather;

@Mapper(componentModel = "spring", uses = LocationEntityMapper.class)
public interface WeatherEntityMapper {

  Weather map(WeatherEntity weatherEntity);

  List<Weather> mapList(List<WeatherEntity> weatherEntity);

}
