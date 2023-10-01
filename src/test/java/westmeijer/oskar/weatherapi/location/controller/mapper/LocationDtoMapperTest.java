package westmeijer.oskar.weatherapi.location.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationDto;

public class LocationDtoMapperTest {

  private final LocationDtoMapper locationDtoMapper = Mappers.getMapper(LocationDtoMapper.class);

  @Test
  public void shouldMapToLocation() {
    Location location = new Location(
        1,
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        Instant.now().truncatedTo(ChronoUnit.MICROS)
    );

    LocationDto locationDto = locationDtoMapper.map(location);

    assertThat(locationDto)
        .returns(location.localZipCode(), LocationDto::getLocalZipCode)
        .returns(location.cityName(), LocationDto::getCityName)
        .returns(location.country(), LocationDto::getCountry)
        .returns(location.openWeatherApiLocationCode(), LocationDto::getLocationCode)
        .returns(location.lastImportAt(), LocationDto::getLastImportAt);
  }

  @Test
  public void shouldMapListToLocations() {

    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);

    Location luebeck = new Location(
        1,
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        now
    );

    Location hamburg = new Location(
        1,
        "20095",
        "2911298",
        "Hamburg",
        "Germany",
        now
    );

    // expected mappings
    LocationDto expectedLuebeck = new LocationDto()
        .localZipCode("23552")
        .locationCode("2875601")
        .cityName("Lübeck")
        .country("Germany")
        .lastImportAt(now);

    LocationDto expectedHamburg = new LocationDto()
        .localZipCode("20095")
        .locationCode("2911298")
        .cityName("Hamburg")
        .country("Germany")
        .lastImportAt(now);

    List<LocationDto> locations = locationDtoMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations).hasSize(2)
        .containsExactlyInAnyOrder(expectedLuebeck, expectedHamburg);
  }

}
