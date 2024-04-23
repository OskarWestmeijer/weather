package westmeijer.oskar.weatherapi.location.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;

public class LocationEntityImportMapperTest {

  @Autowired
  private final LocationEntityImportMapper locationEntityImportMapper = Mappers.getMapper(LocationEntityImportMapper.class);

  @Test
  void shouldMapToLocationEntity() {

    Location expectedLocation = TestLocationFactory.location();
    LocationEntity actualLocation = locationEntityImportMapper.mapToLocationEntity(expectedLocation);

    assertThat(actualLocation)
        .returns(expectedLocation.locationId(), LocationEntity::getId)
        .returns(expectedLocation.uuid(), LocationEntity::getUuid)
        .returns(expectedLocation.latitude(), LocationEntity::getLatitude)
        .returns(expectedLocation.longitude(), LocationEntity::getLongitude)
        .returns(expectedLocation.openWeatherApiLocationCode(), LocationEntity::getOpenWeatherApiLocationCode)
        .returns(expectedLocation.localZipCode(), LocationEntity::getLocalZipCode)
        .returns(expectedLocation.cityName(), LocationEntity::getCityName)
        .returns(expectedLocation.country(), LocationEntity::getCountry)
        .returns(expectedLocation.countryCode(), LocationEntity::getCountryCode)
        .returns(expectedLocation.lastImportAt(), LocationEntity::getLastImportAt);

    assertThat(actualLocation.getModifiedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.SECONDS));
    assertThat(actualLocation.getWeather()).hasSize(1);
  }


  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowOnNullOrEmptyWeather(List<WeatherEntity> weatherList) {
    var locationEntity = mock(LocationEntity.class);
    locationEntity.setWeather(weatherList);

    thenThrownBy(() -> locationEntityImportMapper.setModifiedAt(mock(LocationEntity.class), mock(Location.class)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Only one weather record can be imported");
  }

  @Test
  void shouldApplyModifiedAtTs() {
    var locationEntity = spy(LocationEntity.class);
    WeatherEntity weatherEntity = spy(WeatherEntity.class);
    locationEntity.setWeather(List.of(weatherEntity));

    var tsBeforeCall = Instant.now();
    locationEntityImportMapper.setModifiedAt(locationEntity, mock(Location.class));

    assertThat(weatherEntity.getModifiedAt()).isCloseTo(tsBeforeCall, within(1, ChronoUnit.SECONDS));
  }

}
