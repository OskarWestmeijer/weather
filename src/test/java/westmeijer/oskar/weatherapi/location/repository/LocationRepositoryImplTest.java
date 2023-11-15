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
    given(locationJpaRepository.getNextImportLocation()).willReturn(locationEntity);

    Location expectedLocation = mock(Location.class);
    given(locationEntityMapper.map(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationJpaRepository).should().getNextImportLocation();
    then(locationEntityMapper).should().map(locationEntity);
  }

  @Test
  public void shouldUpdateImportTs() {
    Integer locationId = 1;

    locationRepository.updateLastImportAt(locationId);

    then(locationJpaRepository).should().updateLastImportAt(locationId);
  }

  @Test
  public void updateImportTs_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.updateLastImportAt(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.getById(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNotFound() {
    Integer locationId = 1;
    given(locationJpaRepository.getById(locationId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> locationRepository.getById(locationId))
        .isInstanceOf(LocationNotSupportedException.class)
        .hasMessageContaining("Location lookup for locationId  failed. locationId: 1");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).should().getById(locationId);
  }

  @Test
  public void shouldFindById() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    Location expectedLocation = mock(Location.class);
    Integer locationId = 1;

    given(locationJpaRepository.getById(locationId)).willReturn(Optional.of(locationEntity));
    given(locationEntityMapper.map(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getById(locationId);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().map(locationEntity);
    then(locationJpaRepository).should().getById(locationId);
  }

}
