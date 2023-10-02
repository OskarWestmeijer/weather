package westmeijer.oskar.weatherapi.location.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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
        UUID.randomUUID(),
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        "GER",
        Instant.now().truncatedTo(ChronoUnit.MICROS)
    );

    LocationDto locationDto = locationDtoMapper.map(location);

    assertThat(locationDto)
        .returns(location.uuid(), LocationDto::getUuid)
        .returns(location.localZipCode(), LocationDto::getLocalZipCode)
        .returns(location.cityName(), LocationDto::getCityName)
        .returns(location.country(), LocationDto::getCountry)
        .returns(location.countryCode(), LocationDto::getCountryCode)
        .returns(location.openWeatherApiLocationCode(), LocationDto::getLocationCode)
        .returns(location.lastImportAt(), LocationDto::getLastImportAt);
  }

  @Test
  public void shouldMapListToLocations() {

    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);

    Location luebeck = new Location(
        1,
        UUID.randomUUID(),
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        "GER",
        now
    );

    Location hamburg = new Location(
        1,
        UUID.randomUUID(),
        "20095",
        "2911298",
        "Hamburg",
        "Germany",
        "GER",
        now
    );

    // expected mappings
    LocationDto expectedLuebeck = new LocationDto()
        .uuid(luebeck.uuid())
        .localZipCode("23552")
        .locationCode("2875601")
        .cityName("Lübeck")
        .country("Germany")
        .countryCode("GER")
        .lastImportAt(luebeck.lastImportAt());

    LocationDto expectedHamburg = new LocationDto()
        .uuid(hamburg.uuid())
        .localZipCode("20095")
        .locationCode("2911298")
        .cityName("Hamburg")
        .country("Germany")
        .countryCode("GER")
        .lastImportAt(hamburg.lastImportAt());

    List<LocationDto> locations = locationDtoMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations).hasSize(2)
        .containsExactlyInAnyOrder(expectedLuebeck, expectedHamburg);
  }

}
