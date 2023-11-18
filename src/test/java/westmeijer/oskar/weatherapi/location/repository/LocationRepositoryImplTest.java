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
  public void shouldGetAllOmitWeather() {
    List<LocationEntity> locationEntityList = List.of(mock(LocationEntity.class));
    given(locationJpaRepository.findAll()).willReturn(locationEntityList);

    List<Location> expectedLocationList = List.of(mock(Location.class));
    given(locationEntityMapper.mapToLocationListWithoutWeather(locationEntityList)).willReturn(expectedLocationList);

    List<Location> actualLocationList = locationRepository.getAllOmitWeather();

    assertThat(actualLocationList).isEqualTo(expectedLocationList);
    then(locationJpaRepository).should().findAll();
    then(locationEntityMapper).should().mapToLocationListWithoutWeather(locationEntityList);
  }

  @Test
  public void shouldGetNextImportLocation() {
    LocationEntity locationEntity = mock(LocationEntity.class);
    given(locationJpaRepository.getNextImportLocation()).willReturn(locationEntity);

    Location expectedLocation = mock(Location.class);
    given(locationEntityMapper.mapToLocationWithoutWeather(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getNextImportLocation();

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationJpaRepository).should().getNextImportLocation();
    then(locationEntityMapper).should().mapToLocationWithoutWeather(locationEntity);
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
    given(locationEntityMapper.mapToLocationWithoutWeather(locationEntity)).willReturn(expectedLocation);

    Location actualLocation = locationRepository.getByIdOmitWeather(locationId);

    assertThat(actualLocation).isEqualTo(expectedLocation);
    then(locationEntityMapper).should().mapToLocationWithoutWeather(locationEntity);
    then(locationJpaRepository).should().getById(locationId);
  }

}
