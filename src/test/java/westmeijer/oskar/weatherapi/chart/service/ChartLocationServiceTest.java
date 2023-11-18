package westmeijer.oskar.weatherapi.chart.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.chart.service.mapper.ChartLocationMapper;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class ChartLocationServiceTest {

  @Mock
  private LocationService locationService;

  @Mock
  private ChartLocationMapper chartLocationMapper;

  @InjectMocks
  ChartLocationsService chartLocationsService;

  @Test
  void shouldGetChartLocations() {

    Location location = mock(Location.class);
    given(locationService.getAllWithLatest()).willReturn(List.of(location));

    ChartLocation expectedChartLocation = mock(ChartLocation.class);
    given(chartLocationMapper.mapToChartLocationList(List.of(location))).willReturn(List.of(expectedChartLocation));

    List<ChartLocation> actualChartLocationList = chartLocationsService.getChartLocations();

    assertThat(actualChartLocationList).isEqualTo(List.of(expectedChartLocation));
    then(locationService).should().getAllWithLatest();
    then(chartLocationMapper).should().mapToChartLocationList(List.of(location));

  }

}
