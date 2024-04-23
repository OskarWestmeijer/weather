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
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityImportMapper;
import westmeijer.oskar.weatherapi.location.repository.mapper.LocationEntityMapper;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@ExtendWith(MockitoExtension.class)
public class LocationRepositoryImplTest {

  @Mock
  private LocationJpaRepository locationJpaRepository;

  @Mock
  private LocationEntityMapper locationEntityMapper;

  @Mock
  private LocationEntityImportMapper locationEntityImportMapper;

  @InjectMocks
  private LocationRepositoryImpl locationRepository;

  @Test
  public void shouldGetAllOmitWeather() {
    List<LocationEntity> locationEntityList = List.of(mock(LocationEntity.class));
    given(locationJpaRepository.findAll()).willReturn(locationEntityList);

    List<Location> expectedLocationList = List.of(mock(Location.class));
    given(locationEntityMapper.mapToLocationListWithEmptyWeather(locationEntityList)).willReturn(expectedLocationList);

    List<Location> actualLocationList = locationRepository.getAllOmitWeather();

    assertThat(actualLocationList).isEqualTo(expectedLocationList);
    then(locationJpaRepository).should().findAll();
    then(locationEntityMapper).should().mapToLocationListWithEmptyWeather(locationEntityList);
  }

  @Test
  public void shouldGetNextImportLocation() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    given(locationJpaRepository.getNextImportLocation()).willReturn(locationEntity);

    Location expectedLocation = mock(Location.class);
    given(locationEntityMapper.mapToLocationWithEmptyWeather(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationJpaRepository).should().getNextImportLocation();
    then(locationEntityMapper).should().mapToLocationWithEmptyWeather(locationEntity);
  }

  @Test
  public void shouldGetAllWithLatest() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    given(locationJpaRepository.getAllWithLatest()).willReturn(List.of(locationEntity));

    Location expectedLocation = mock(Location.class);
    given(locationEntityMapper.mapToLocationList(List.of(locationEntity))).willReturn(List.of(expectedLocation));

    List<Location> actualLocationList = locationRepository.getAllWithLatest();

    assertThat(actualLocationList).isEqualTo(List.of(expectedLocation));
    then(locationJpaRepository).should().getAllWithLatest();
    then(locationEntityMapper).should().mapToLocationList(List.of(locationEntity));
  }

  @Test
  public void getByIdOmitWeather_throwsExceptionOnNullParam() {
    assertThatThrownBy(() -> locationRepository.getByIdOmitWeather(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("locationId is required");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).shouldHaveNoInteractions();
  }

  @Test
  public void getByIdOmitWeather_throwsExceptionOnNotFound() {
    Integer locationId = 1;
    given(locationJpaRepository.getById(locationId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> locationRepository.getByIdOmitWeather(locationId))
        .isInstanceOf(LocationNotSupportedException.class)
        .hasMessageContaining("Location lookup for locationId  failed. locationId: 1");

    then(locationEntityMapper).shouldHaveNoInteractions();
    then(locationJpaRepository).should().getById(locationId);
  }

  @Test
  public void shouldGetByIdOmitWeather() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    Location expectedLocation = mock(Location.class);
    Integer locationId = 1;

    given(locationJpaRepository.getById(locationId)).willReturn(Optional.of(locationEntity));
    given(locationEntityMapper.mapToLocationWithEmptyWeather(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getByIdOmitWeather(locationId);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().mapToLocationWithEmptyWeather(locationEntity);
    then(locationJpaRepository).should().getById(locationId);
  }

  @Test
  public void shouldSaveAndFlush() {
    Location updatedLocation = mock(Location.class);
    LocationEntity locationEntity = mock(LocationEntity.class);
    given(locationEntityImportMapper.mapToLocationEntity(updatedLocation)).willReturn(locationEntity);

    locationRepository.save(updatedLocation);

    then(locationEntityImportMapper).should().mapToLocationEntity(updatedLocation);
    then(locationJpaRepository).should().saveAndFlush(locationEntity);
  }

  @Test
  public void saveAndFlushThrowsNpe() {
    assertThatThrownBy(() -> locationRepository.save(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location is required");

    then(locationJpaRepository).shouldHaveNoInteractions();
    then(locationEntityImportMapper).shouldHaveNoInteractions();
  }

}
