package westmeijer.oskar.weatherapi.chart.service.mapper;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface ChartLocationMapper {

  @Mapping(target = "locationId", source = "location.locationId")
  ChartLocation mapTo(Location location, Weather weather);

  @IterableMapping(qualifiedByName = "mapToChartLocation")
  List<ChartLocation> mapToChartLocationList(List<Location> location);

  @Named("mapToChartLocation")
  default ChartLocation mapToChartLocation(Location location) {
    requireNonNull(location, "location is required");
    requireNonNull(location.getWeather(), "weather is required");
    checkArgument(location.getWeather().size() == 1);

    Weather w = location.getWeather().get(0);
    return new ChartLocation(location.getLocationId(), location.getCityName(), location.getCountryCode(),
        w.getTemperature(), w.getHumidity(), w.getWindSpeed(), w.getRecordedAt());
  }

}
