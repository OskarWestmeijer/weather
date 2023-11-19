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

  @IterableMapping(qualifiedByName = "mapToOverview")
  List<Overview> mapToOverviewList(List<Location> location);

  @Named("mapToOverview")
  default Overview mapToOverview(Location location) {
    requireNonNull(location, "location is required");
    requireNonNull(location.getWeather(), "weather is required");
    checkArgument(location.getWeather().size() == 1, "requires exactly one weather element");

    Weather w = location.getWeather().get(0);
    return new Overview(location.getLocationId(), location.getCityName(), location.getCountryCode(),
        w.getTemperature(), w.getHumidity(), w.getWindSpeed(), w.getRecordedAt());
  }

}
