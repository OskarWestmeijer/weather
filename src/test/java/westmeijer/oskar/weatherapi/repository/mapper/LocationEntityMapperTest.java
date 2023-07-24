package westmeijer.oskar.weatherapi.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;


public class LocationEntityMapperTest {

  private final LocationEntityMapper locationEntityMapper = Mappers.getMapper(LocationEntityMapper.class);

  @Test
  public void shouldMapToLocation() {
    LocationEntity locationEntity = LocationEntity.builder()
        .locationCode("2875601")
        .localZipCode("23552")
        .cityName("Lübeck")
        .country("Germany")
        .modifiedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS))
        .lastImportAt(Instant.now().truncatedTo(ChronoUnit.MILLIS))
        .build();

    Location location = locationEntityMapper.map(locationEntity);

    assertThat(location.locationCode()).isEqualTo(locationEntity.getLocationCode());
    assertThat(location.localZipCode()).isEqualTo(locationEntity.getLocalZipCode());
    assertThat(location.cityName()).isEqualTo(locationEntity.getCityName());
    assertThat(location.country()).isEqualTo(locationEntity.getCountry());
    assertThat(location.lastImportAt()).isEqualTo(locationEntity.getLastImportAt());
  }

  @Test
  public void shouldMapListToLocations() {
    LocationEntity luebeck = LocationEntity.builder()
        .locationCode("2875601")
        .localZipCode("23552")
        .cityName("Lübeck")
        .country("Germany")
        .modifiedAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .lastImportAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .build();

    LocationEntity hamburg = LocationEntity.builder()
        .locationCode("2911298")
        .localZipCode("20095")
        .cityName("Hamburg")
        .country("Germany")
        .modifiedAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .lastImportAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .build();

    List<Location> locations = locationEntityMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations.size()).isEqualTo(2);
    assertThat(locations).extracting("locationCode", "localZipCode", "cityName", "country")
        .containsOnlyOnce(Tuple.tuple(luebeck.getLocationCode(), luebeck.getLocalZipCode(), luebeck.getCityName(), luebeck.getCountry()))
        .containsOnlyOnce(Tuple.tuple(hamburg.getLocationCode(), hamburg.getLocalZipCode(), hamburg.getCityName(), hamburg.getCountry()));
  }

  @Test
  public void shouldMapToLocationEntity() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
    Location location = new Location("20095", "2911298", "Hamburg", "Germany", now);

    LocationEntity locationEntity = locationEntityMapper.map(location);

    assertThat(locationEntity.getLocalZipCode()).isEqualTo(location.localZipCode());
    assertThat(locationEntity.getLocationCode()).isEqualTo(location.locationCode());
    assertThat(locationEntity.getCountry()).isEqualTo(location.country());
    assertThat(locationEntity.getCityName()).isEqualTo(location.cityName());
    assertThat(locationEntity.getLastImportAt()).isEqualTo(location.lastImportAt());
    assertThat(locationEntity.getModifiedAt()).isCloseTo(now, within(1, ChronoUnit.SECONDS));
  }

}
