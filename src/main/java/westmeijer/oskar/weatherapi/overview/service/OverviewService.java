package westmeijer.oskar.weatherapi.overview.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.overview.service.mapper.OverviewMapper;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;

@Service
@RequiredArgsConstructor
public class OverviewService {

  private final LocationService locationService;

  private final OverviewMapper overviewMapper;

  public List<Overview> getOverview() {
    List<Location> locations = locationService.getAllWithLatest();
    return overviewMapper.mapToChartLocationList(locations);
  }

}
