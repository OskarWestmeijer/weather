package westmeijer.oskar.weatherapi.chart.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import westmeijer.oskar.weatherapi.chart.controller.mapper.ChartLocationsDtoMapper;
import westmeijer.oskar.weatherapi.chart.service.ChartLocationsService;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.openapi.server.chart.api.ChartApi;
import westmeijer.oskar.weatherapi.openapi.server.chart.model.ChartLocationsResponse;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ChartLocationsController implements ChartApi {

  private final ChartLocationsDtoMapper chartLocationsResponseMapper;

  private final ChartLocationsService chartLocationsService;

  @Override
  public ResponseEntity<ChartLocationsResponse> getChartLocations() {
    List<ChartLocation> chartLocations = chartLocationsService.getChartLocations();
    ChartLocationsResponse chartLocationsResponse = chartLocationsResponseMapper.mapToResponse(chartLocations);
    return ResponseEntity.ok(chartLocationsResponse);
  }
}
