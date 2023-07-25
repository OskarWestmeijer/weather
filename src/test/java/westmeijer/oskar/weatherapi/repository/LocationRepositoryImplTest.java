package westmeijer.oskar.weatherapi.repository;

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
import westmeijer.oskar.weatherapi.controller.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;

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

    Location expectedLocation = mock(Location.class);
    given(locationEntityMapper.map(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationJpaRepository).should().findFirstByOrderByLastImportAtAsc();
    then(locationEntityMapper).should().map(locationEntity);
  }

  @Test
  public void shouldSaveAndFlush() {
    Location newLocation = mock(Location.class);
    LocationEntity newEntity = mock(LocationEntity.class);
    LocationEntity savedEntity = mock(LocationEntity.class);
    Location expectedLocation = mock(Location.class);

    given(locationEntityMapper.map(newLocation)).willReturn(newEntity);
    given(locationJpaRepository.saveAndFlush(newEntity)).willReturn(savedEntity);
    given(locationEntityMapper.map(savedEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.saveAndFlush(newLocation);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().map(newLocation);
    then(locationJpaRepository).should().saveAndFlush(newEntity);
    then(locationEntityMapper).should().map(savedEntity);
  }

  @Test
  public void saveAndFlush_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.saveAndFlush(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location must not be null");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.findById(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("localZipCode must not be null");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void findById_throwsExceptionOnNotFound() {
    String localZipCode = "20535";
    given(locationJpaRepository.findById(localZipCode)).willThrow(new LocationNotSupportedException(localZipCode));

    assertThatThrownBy(() -> locationRepository.findById(localZipCode))
        .isInstanceOf(LocationNotSupportedException.class)
        .hasMessageContaining("Location lookup for localZipCode  failed. localZipCode: 20535");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).should().findById(localZipCode);
  }

  @Test
  public void shouldFindById() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    Location expectedLocation = mock(Location.class);
    String localZipCode = "20535";

    given(locationJpaRepository.findById(localZipCode)).willReturn(Optional.of(locationEntity));
    given(locationEntityMapper.map(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.findById(localZipCode);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().map(locationEntity);
    then(locationJpaRepository).should().findById(localZipCode);
  }

}
