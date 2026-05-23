package westmeijer.oskar.weatherapi.application.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.domain.model.Location;
import westmeijer.oskar.weatherapi.domain.model.Overview;

@Service
@RequiredArgsConstructor
public class OverviewService {

  private final LocationService locationService;

  private final OverviewMapper overviewMapper;

  public List<Overview> getOverview() {
    List<Location> locations = locationService.getAllWithLatest();
    return overviewMapper.mapToOverviewList(locations);
  }

}
