package westmeijer.oskar.weatherapi.overview.service.mapper;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
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
    checkArgument(CollectionUtils.size(location.weather()) == 1, "requires exactly one weather element");

    Weather w = location.weather().getFirst();
    return new Overview(location.locationId(), location.cityName(), location.countryCode(),
        w.temperature(), w.humidity(), w.windSpeed(), w.recordedAt());
  }

}
