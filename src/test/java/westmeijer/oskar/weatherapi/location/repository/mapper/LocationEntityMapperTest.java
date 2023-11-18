package westmeijer.oskar.weatherapi.location.repository.mapper;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
  public void shouldMapToLocationWithoutWeather() {
    LocationEntity locationEntity = TestLocationFactory.locationEntityWithoutWeather();

    Location location = locationEntityMapper.mapToLocationWithoutWeather(locationEntity);

    assertThat(location)
        .returns(locationEntity.getId(), Location::getLocationId)
        .returns(locationEntity.getOpenWeatherApiLocationCode(), Location::getOpenWeatherApiLocationCode)
        .returns(locationEntity.getLocalZipCode(), Location::getLocalZipCode)
        .returns(locationEntity.getCityName(), Location::getCityName)
        .returns(locationEntity.getCountry(), Location::getCountry)
        .returns(locationEntity.getCountryCode(), Location::getCountryCode)
        .returns(locationEntity.getLastImportAt(), Location::getLastImportAt)
        .returns(locationEntity.getWeather(), l -> new ArrayList<Location>());
  }

  @Test
  public void mapToLocationListWithoutWeather() {
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

    List<Location> locations = locationEntityMapper.mapToLocationListWithoutWeather(List.of(luebeck, hamburg));

    assertThat(locations)
        .hasSize(2);
    // TODO: assert elements
  }

}
