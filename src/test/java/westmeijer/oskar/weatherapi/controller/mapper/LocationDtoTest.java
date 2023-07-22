package westmeijer.oskar.weatherapi.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.controller.model.LocationDto;
import westmeijer.oskar.weatherapi.service.model.Location;

public class LocationDtoTest {

  private final LocationDtoMapper locationDtoMapper = Mappers.getMapper(LocationDtoMapper.class);

  @Test
  public void shouldMapToLocation() {
    Location location = new Location(
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS)
    );

    LocationDto locationDto = locationDtoMapper.map(location);

    assertThat(locationDto.locationCode()).isEqualTo(location.locationCode());
    assertThat(locationDto.localZipCode()).isEqualTo(location.localZipCode());
    assertThat(locationDto.cityName()).isEqualTo(location.cityName());
    assertThat(locationDto.country()).isEqualTo(location.country());
    assertThat(locationDto.modifiedAt()).isEqualTo(location.modifiedAt());
    assertThat(locationDto.lastImportAt()).isEqualTo(location.lastImportAt());
  }

  @Test
  public void shouldMapListToLocations() {
    Location luebeck = new Location(
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS)
    );

    Location hamburg = new Location(
        "20095",
        "2911298",
        "Hamburg",
        "Germany",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS)
    );

    List<LocationDto> locations = locationDtoMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations.size()).isEqualTo(2);
    assertThat(locations).extracting("locationCode", "localZipCode", "cityName", "country")
        .containsOnlyOnce(Tuple.tuple("2911298", "20095", "Hamburg", "Germany"))
        .containsOnlyOnce(Tuple.tuple("2875601", "23552", "Lübeck", "Germany"));
  }

}
