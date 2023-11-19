package westmeijer.oskar.weatherapi.overview.service.mapper;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Mapper(componentModel = "spring")
public interface OverviewMapper {

  @Mapping(target = "locationId", source = "location.locationId")
  Overview mapTo(Location location, Weather weather);

  @IterableMapping(qualifiedByName = "mapToChartLocation")
  List<Overview> mapToChartLocationList(List<Location> location);

  @Named("mapToChartLocation")
  default Overview mapToChartLocation(Location location) {
    requireNonNull(location, "location is required");
    requireNonNull(location.getWeather(), "weather is required");
    checkArgument(location.getWeather().size() == 1);

    Weather w = location.getWeather().get(0);
    return new Overview(location.getLocationId(), location.getCityName(), location.getCountryCode(),
        w.getTemperature(), w.getHumidity(), w.getWindSpeed(), w.getRecordedAt());
  }

}
