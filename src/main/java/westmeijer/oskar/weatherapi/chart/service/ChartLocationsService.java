package westmeijer.oskar.weatherapi.chart.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.chart.service.mapper.ChartLocationMapper;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;

@Service
@RequiredArgsConstructor
public class ChartLocationsService {

  private final LocationService locationService;

  private final WeatherService weatherService;

  private final ChartLocationMapper chartLocationMapper;

  public List<ChartLocation> getChartLocations() {
    List<Location> locations = locationService.getAllOmitWeather();
    return locations.stream()
        .map(location -> chartLocationMapper.mapTo(location, weatherService.getLatestWeather(location.getLocationId())))
        .toList();
  }
}
