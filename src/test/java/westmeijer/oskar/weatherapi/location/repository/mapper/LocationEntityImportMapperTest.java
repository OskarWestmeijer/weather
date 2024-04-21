package westmeijer.oskar.weatherapi.location.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

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

}
