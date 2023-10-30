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
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.location.repository.LocationRepository;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

  @Mock
  private LocationRepository locationRepository;

  @InjectMocks
  private LocationService locationService;

  @Test
  public void shouldFindByIdLocalZipCode() {
    String localZipCode = "20535";
    Location expectedLocation = mock(Location.class);
    given(locationRepository.getByLocalZipCode(localZipCode)).willReturn(expectedLocation);

    Location actualLocation = locationService.getByLocalZipCode(localZipCode);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getByLocalZipCode(localZipCode);
  }

  @Test
  public void findById_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.getByLocalZipCode(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("localZipCode must not be null");

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
    ImportJobLocation expectedLocation = mock(ImportJobLocation.class);
    given(locationRepository.getNextImportLocation()).willReturn(expectedLocation);

    ImportJobLocation actualLocation = locationService.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getNextImportLocation();
  }

  @Test
  public void updateLastImportedAt_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.updateLastImportAt(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location must not be null");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldupdateLastImportedAt() {
    ImportJobLocation location = mock(ImportJobLocation.class);
    locationService.updateLastImportAt(location);
    then(locationRepository).should().updateLastImportAt(location);
  }

}
