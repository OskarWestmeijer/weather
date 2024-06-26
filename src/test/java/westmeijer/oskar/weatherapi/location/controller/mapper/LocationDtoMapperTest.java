package westmeijer.oskar.weatherapi.location.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.server.model.LocationDto;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    LocationDtoMapperImpl.class,
})
public class LocationDtoMapperTest {

  @Autowired
  private LocationDtoMapper locationDtoMapper;

  @Test
  public void shouldMapToLocation() {
    Location location = TestLocationFactory.locationWithoutWeather();

    LocationDto locationDto = locationDtoMapper.map(location);

    assertThat(locationDto)
        .returns(location.locationId(), LocationDto::getLocationId)
        .returns(location.localZipCode(), LocationDto::getLocalZipCode)
        .returns(location.cityName(), LocationDto::getCityName)
        .returns(location.country(), LocationDto::getCountry)
        .returns(location.countryCode(), LocationDto::getCountryCode)
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
        "51.659088",
        "6.966170",
        now,
        Collections.emptyList()
    );

    Location hamburg = new Location(
        2,
        UUID.randomUUID(),
        "20095",
        "2911298",
        "Hamburg",
        "Germany",
        "GER",
        "51.659088",
        "6.966170",
        now,
        Collections.emptyList()
    );

    // expected mappings
    LocationDto expectedLuebeck = new LocationDto()
        .locationId(1)
        .localZipCode("23552")
        .cityName("Lübeck")
        .country("Germany")
        .countryCode("GER")
        .lastImportAt(luebeck.lastImportAt());

    LocationDto expectedHamburg = new LocationDto()
        .locationId(2)
        .localZipCode("20095")
        .cityName("Hamburg")
        .country("Germany")
        .countryCode("GER")
        .lastImportAt(hamburg.lastImportAt());

    List<LocationDto> locations = locationDtoMapper.mapList(List.of(luebeck, hamburg));

    assertThat(locations).hasSize(2)
        .containsExactlyInAnyOrder(expectedLuebeck, expectedHamburg);
  }

}
