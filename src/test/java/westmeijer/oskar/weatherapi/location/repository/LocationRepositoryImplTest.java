package westmeijer.oskar.weatherapi.location.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class LocationRepositoryImplTest {

  @Mock
  private LocationJpaRepository locationJpaRepository;

  @Mock
  private LocationEntityMapper locationEntityMapper;

  @InjectMocks
  private LocationRepositoryImpl locationRepository;

  @Test
  public void shouldGetAll() {
    List<LocationEntity> locationEntityList = List.of(mock(LocationEntity.class));
    given(locationJpaRepository.findAll()).willReturn(locationEntityList);

    List<Location> expectedLocationList = List.of(mock(Location.class));
    given(locationEntityMapper.mapList(locationEntityList)).willReturn(expectedLocationList);

    List<Location> actualLocationList = locationRepository.getAll();

    assertThat(actualLocationList).isEqualTo(expectedLocationList);
    then(locationJpaRepository).should().findAll();
    then(locationEntityMapper).should().mapList(locationEntityList);
  }

  @Test
  public void shouldGetNextImportLocation() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    given(locationJpaRepository.findFirstByOrderByLastImportAtAsc()).willReturn(locationEntity);

    ImportJobLocation expectedLocation = mock(ImportJobLocation.class);
    given(locationEntityMapper.mapToJobLocation(locationEntity)).willReturn(expectedLocation);

    ImportJobLocation actualLocation = locationRepository.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationJpaRepository).should().findFirstByOrderByLastImportAtAsc();
    then(locationEntityMapper).should().mapToJobLocation(locationEntity);
  }

  @Test
  public void shouldUpdateImportTs() {
    ImportJobLocation newLocation = mock(ImportJobLocation.class);

    locationRepository.updateLastImportAt(newLocation);

    then(locationJpaRepository).should().updateLastImportAt(newLocation.id());
  }

  @Test
  public void updateImportTs_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.updateLastImportAt(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location must not be null");

    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.findByLocalZipCode(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("localZipCode must not be null");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNotFound() {
    String localZipCode = "20535";
    given(locationJpaRepository.findByLocalZipCode(localZipCode)).willReturn(Optional.empty());

    assertThatThrownBy(() -> locationRepository.findByLocalZipCode(localZipCode))
        .isInstanceOf(LocationNotSupportedException.class)
        .hasMessageContaining("Location lookup for localZipCode  failed. localZipCode: 20535");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).should().findByLocalZipCode(localZipCode);
  }

  @Test
  public void shouldFindById() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    Location expectedLocation = mock(Location.class);
    String localZipCode = "20535";

    given(locationJpaRepository.findByLocalZipCode(localZipCode)).willReturn(Optional.of(locationEntity));
    given(locationEntityMapper.map(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.findByLocalZipCode(localZipCode);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().map(locationEntity);
    then(locationJpaRepository).should().findByLocalZipCode(localZipCode);
  }

}
