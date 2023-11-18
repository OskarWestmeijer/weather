package westmeijer.oskar.weatherapi.location.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.location.repository.LocationRepository;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

  @Mock
  private LocationRepository locationRepository;

  @InjectMocks
  private LocationService locationService;

  @Test
  public void shouldGetByIdOmitWeather() {
    Integer locationId = 1;
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getByIdOmitWeather(locationId)).willReturn(expectedLocation);

    Location actualLocation = locationService.getByIdOmitWeather(locationId);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getByIdOmitWeather(locationId);
  }

  @Test
  public void getByIdOmitWeather_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.getByIdOmitWeather(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldGetAllOmitWeather() {
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getAllOmitWeather()).willReturn(List.of(expectedLocation));

    List<Location> actualLocations = locationService.getAllOmitWeather();

    assertThat(actualLocations).isEqualTo(List.of(expectedLocation));
    then(locationRepository).should().getAllOmitWeather();
  }

  @Test
  public void shouldGetNextImportLocation() {
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getNextImportLocation()).willReturn(expectedLocation);

    Location actualLocation = locationService.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getNextImportLocation();
  }

}
