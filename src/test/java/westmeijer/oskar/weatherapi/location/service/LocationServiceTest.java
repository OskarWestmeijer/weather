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
  public void shouldFindByLocationId() {
    Integer locationId = 1;
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getById(locationId)).willReturn(expectedLocation);

    Location actualLocation = locationService.getById(locationId);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getById(locationId);
  }

  @Test
  public void findById_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.getById(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldGetAll() {
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getAll()).willReturn(List.of(expectedLocation));

    List<Location> actualLocations = locationService.getAll();

    assertThat(actualLocations).isEqualTo(List.of(expectedLocation));
    then(locationRepository).should().getAll();
  }

  @Test
  public void shouldGetNextImportLocation() {
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getNextImportLocation()).willReturn(expectedLocation);

    Location actualLocation = locationService.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getNextImportLocation();
  }

  @Test
  public void updateLastImportedAt_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.updateLastImportAt(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldUpdateLastImportedAt() {
    Integer locationId = 1;
    locationService.updateLastImportAt(locationId);
    then(locationRepository).should().updateLastImportAt(locationId);
  }

}
