package westmeijer.oskar.weatherapi.location.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;


public class LocationEntityMapperTest {

  private final LocationEntityMapper locationEntityMapper = Mappers.getMapper(LocationEntityMapper.class);

  @Test
  public void shouldMapToLocation() {
    LocationEntity locationEntity = TestLocationFactory.locationEntity();

    Location location = locationEntityMapper.map(locationEntity);

    assertThat(location)
        .returns(locationEntity.getId(), Location::locationId)
        .returns(locationEntity.getOpenWeatherApiLocationCode(), Location::openWeatherApiLocationCode)
        .returns(locationEntity.getLocalZipCode(), Location::localZipCode)
        .returns(locationEntity.getCityName(), Location::cityName)
        .returns(locationEntity.getCountry(), Location::country)
        .returns(locationEntity.getCountryCode(), Location::countryCode)
        .returns(locationEntity.getLastImportAt(), Location::lastImportAt);
    // TODO: assert weather elements
  }

  @Test
  public void shouldMapListToLocations() {
    LocationEntity luebeck = LocationEntity.builder()
        .id(1)
        .uuid(UUID.randomUUID())
        .openWeatherApiLocationCode("2875601")
        .localZipCode("23552")
        .cityName("Lübeck")
        .country("Germany")
        .countryCode("GER")
        .modifiedAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .lastImportAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .latitude("1")
        .longitude("2")
        .weather(Collections.emptyList())
        .build();

    LocationEntity hamburg = LocationEntity.builder()
        .id(2)
        .uuid(UUID.randomUUID())
        .openWeatherApiLocationCode("2911298")
        .localZipCode("20095")
        .cityName("Hamburg")
        .country("Germany")
        .countryCode("GER")
        .modifiedAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .lastImportAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .latitude("1")
        .longitude("2")
        .weather(Collections.emptyList())
        .build();

    List<Location> locations = locationEntityMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations)
        .hasSize(2);
    // TODO: assert elements
  }

  @Test
  public void shouldMapToLocationEntity() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    Location location = TestLocationFactory.location();

    LocationEntity locationEntity = locationEntityMapper.map(location);

    assertThat(locationEntity.getId()).isEqualTo(location.locationId());
    assertThat(locationEntity.getUuid()).isEqualTo(location.uuid());
    assertThat(locationEntity.getLocalZipCode()).isEqualTo(location.localZipCode());
    assertThat(locationEntity.getOpenWeatherApiLocationCode()).isEqualTo(location.openWeatherApiLocationCode());
    assertThat(locationEntity.getCountry()).isEqualTo(location.country());
    assertThat(locationEntity.getCountryCode()).isEqualTo(location.countryCode());
    assertThat(locationEntity.getCityName()).isEqualTo(location.cityName());
    assertThat(locationEntity.getLastImportAt()).isEqualTo(location.lastImportAt());
    assertThat(locationEntity.getModifiedAt()).isCloseTo(now, within(1, ChronoUnit.SECONDS));
  }

}
