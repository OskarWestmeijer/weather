package westmeijer.oskar.weatherapi.overview.service;


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
import westmeijer.oskar.weatherapi.overview.service.mapper.OverviewMapper;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class OverviewServiceTest {

  @Mock
  private LocationService locationService;

  @Mock
  private OverviewMapper overviewMapper;

  @InjectMocks
  OverviewService overviewService;

  @Test
  void shouldGetOverview() {

    Location location = mock(Location.class);
    given(locationService.getAllWithLatest()).willReturn(List.of(location));

    Overview expectedOverview = mock(Overview.class);
    given(overviewMapper.mapToChartLocationList(List.of(location))).willReturn(List.of(expectedOverview));

    List<Overview> actualOverviewList = overviewService.getOverview();

    assertThat(actualOverviewList).isEqualTo(List.of(expectedOverview));
    then(locationService).should().getAllWithLatest();
    then(overviewMapper).should().mapToChartLocationList(List.of(location));

  }

}
