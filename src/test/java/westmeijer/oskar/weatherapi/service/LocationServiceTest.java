package westmeijer.oskar.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

  @Mock
  private LocationRepository locationRepository;

  @InjectMocks
  private LocationService locationService;

  @Test
  public void shouldFindByIdLocalZipCode() {
    String localZipCode = "20535";
    Location expectedLocation = new Location(1, localZipCode, "2875601", "Lübeck", "Germany", Instant.now());
    given(locationRepository.findByLocalZipCode(localZipCode)).willReturn(expectedLocation);

    Location actualLocation = locationService.findByLocalZipCode(localZipCode);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().findByLocalZipCode(localZipCode);
  }

  @Test
  public void findById_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.findByLocalZipCode(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("localZipCode must not be null");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldGetAll() {
    Location expectedLocation = new Location(1, "20535", "2875601", "Lübeck", "Germany", Instant.now());
    given(locationRepository.getAll()).willReturn(List.of(expectedLocation));

    List<Location> actualLocations = locationService.getAll();

    assertThat(actualLocations).isEqualTo(List.of(expectedLocation));
    then(locationRepository).should().getAll();
  }

  @Test
  public void shouldGetNextImportLocation() {
    Location expectedLocation = new Location(1, "20535", "2875601", "Lübeck", "Germany", Instant.now());
    given(locationRepository.getNextImportLocation()).willReturn(expectedLocation);

    Location actualLocation = locationService.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().getNextImportLocation();
  }

  @Test
  public void saveAndFlush_shouldThrowExceptionOnNullParam() {
    assertThatThrownBy(() -> locationService.saveAndFlush(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location must not be null");

    then(locationRepository).shouldHaveNoInteractions();
  }

  @Test
  public void shouldSaveAndFlush() {
    Location expectedLocation = new Location(1, "20535", "2875601", "Lübeck", "Germany", Instant.now());
    given(locationRepository.saveAndFlush(expectedLocation)).willReturn(expectedLocation);

    Location actualLocation = locationService.saveAndFlush(expectedLocation);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationRepository).should().saveAndFlush(expectedLocation);
  }

}
