package westmeijer.oskar.weatherapi.location.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapper;
import westmeijer.oskar.weatherapi.weather.repository.mapper.WeatherEntityMapperImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WeatherEntityMapperImpl.class,
    LocationEntityMapperImpl.class,
})
public class LocationEntityMapperTest {

  @Autowired
  private LocationEntityMapper locationEntityMapper;

  @Autowired
  private WeatherEntityMapper weatherEntityMapper;

  @Test
  public void shouldMapToLocationWithoutWeather() {
    LocationEntity locationEntity = TestLocationFactory.locationEntityWithoutWeather();

    Location location = locationEntityMapper.mapToLocationWithoutWeather(locationEntity);

    assertThat(location)
        .returns(locationEntity.getId(), Location::locationId)
        .returns(locationEntity.getUuid(), Location::uuid)
        .returns(locationEntity.getLatitude(), Location::latitude)
        .returns(locationEntity.getLongitude(), Location::longitude)
        .returns(locationEntity.getOpenWeatherApiLocationCode(), Location::openWeatherApiLocationCode)
        .returns(locationEntity.getLocalZipCode(), Location::localZipCode)
        .returns(locationEntity.getCityName(), Location::cityName)
        .returns(locationEntity.getCountry(), Location::country)
        .returns(locationEntity.getCountryCode(), Location::countryCode)
        .returns(locationEntity.getLastImportAt(), Location::lastImportAt)
        .returns(locationEntity.getWeather(), l -> new ArrayList<Location>());
  }

  @Test
  public void mapToLocationListWithoutWeather() {
    LocationEntity expectedLocation = TestLocationFactory.locationEntityWithoutWeather();

    List<Location> actualLocationList = locationEntityMapper.mapToLocationListWithoutWeather(List.of(expectedLocation));

    assertThat(actualLocationList).hasSize(1);
    Location actualLocation = actualLocationList.get(0);
    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::locationId)
        .returns(expectedLocation.getUuid(), Location::uuid)
        .returns(expectedLocation.getLatitude(), Location::latitude)
        .returns(expectedLocation.getLongitude(), Location::longitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::openWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::localZipCode)
        .returns(expectedLocation.getCityName(), Location::cityName)
        .returns(expectedLocation.getCountry(), Location::country)
        .returns(expectedLocation.getCountryCode(), Location::countryCode)
        .returns(expectedLocation.getLastImportAt(), Location::lastImportAt);

    assertThat(actualLocation.weather()).isNull();
  }

  @Test
  public void shouldMapToLocation() {
    LocationEntity expectedLocation = TestLocationFactory.locationEntity();

    Location actualLocation = locationEntityMapper.mapToLocation(expectedLocation);

    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::locationId)
        .returns(expectedLocation.getUuid(), Location::uuid)
        .returns(expectedLocation.getLatitude(), Location::latitude)
        .returns(expectedLocation.getLongitude(), Location::longitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::openWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::localZipCode)
        .returns(expectedLocation.getCityName(), Location::cityName)
        .returns(expectedLocation.getCountry(), Location::country)
        .returns(expectedLocation.getCountryCode(), Location::countryCode)
        .returns(expectedLocation.getLastImportAt(), Location::lastImportAt);

    assertThat(actualLocation.weather()).hasSize(1);
  }

  @Test
  public void shouldMapToLocationList() {
    LocationEntity expectedLocation = TestLocationFactory.locationEntity();

    List<Location> actualLocationList = locationEntityMapper.mapToLocationList(List.of(expectedLocation));

    assertThat(actualLocationList).hasSize(1);
    Location actualLocation = actualLocationList.getFirst();

    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::locationId)
        .returns(expectedLocation.getUuid(), Location::uuid)
        .returns(expectedLocation.getLatitude(), Location::latitude)
        .returns(expectedLocation.getLongitude(), Location::longitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::openWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::localZipCode)
        .returns(expectedLocation.getCityName(), Location::cityName)
        .returns(expectedLocation.getCountry(), Location::country)
        .returns(expectedLocation.getCountryCode(), Location::countryCode)
        .returns(expectedLocation.getLastImportAt(), Location::lastImportAt);

    assertThat(actualLocation.weather()).hasSize(1);
  }

}
