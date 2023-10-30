package westmeijer.oskar.weatherapi.chart.service.mapper;

import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface ChartLocationMapper {

  ChartLocation mapTo(Location location, Weather weather);

}
