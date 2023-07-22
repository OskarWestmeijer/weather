package westmeijer.oskar.weatherapi.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.service.model.Location;


public class LocationMapperTest {

  private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

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

    Location location = locationMapper.map(locationEntity);

    assertThat(location.locationCode()).isEqualTo(locationEntity.getLocationCode());
    assertThat(location.localZipCode()).isEqualTo(locationEntity.getLocalZipCode());
    assertThat(location.cityName()).isEqualTo(locationEntity.getCityName());
    assertThat(location.country()).isEqualTo(locationEntity.getCountry());
    assertThat(location.modifiedAt()).isEqualTo(locationEntity.getModifiedAt());
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

    List<Location> locations = locationMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations.size()).isEqualTo(2);
    assertThat(locations).extracting("locationCode", "localZipCode", "cityName", "country")
        .containsOnlyOnce(Tuple.tuple("2911298", "20095", "Hamburg", "Germany"))
        .containsOnlyOnce(Tuple.tuple("2875601", "23552", "Lübeck", "Germany"));
  }

}
