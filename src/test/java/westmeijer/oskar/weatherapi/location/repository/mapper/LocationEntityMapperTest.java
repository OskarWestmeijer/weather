package westmeijer.oskar.weatherapi.location.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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
        .returns(locationEntity.getId(), Location::getLocationId)
        .returns(locationEntity.getUuid(), Location::getUuid)
        .returns(locationEntity.getLatitude(), Location::getLatitude)
        .returns(locationEntity.getLongitude(), Location::getLongitude)
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
    LocationEntity expectedLocation = TestLocationFactory.locationEntityWithoutWeather();

    List<Location> actualLocationList = locationEntityMapper.mapToLocationListWithoutWeather(List.of(expectedLocation));

    assertThat(actualLocationList).hasSize(1);
    Location actualLocation = actualLocationList.get(0);
    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::getLocationId)
        .returns(expectedLocation.getUuid(), Location::getUuid)
        .returns(expectedLocation.getLatitude(), Location::getLatitude)
        .returns(expectedLocation.getLongitude(), Location::getLongitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::getOpenWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::getLocalZipCode)
        .returns(expectedLocation.getCityName(), Location::getCityName)
        .returns(expectedLocation.getCountry(), Location::getCountry)
        .returns(expectedLocation.getCountryCode(), Location::getCountryCode)
        .returns(expectedLocation.getLastImportAt(), Location::getLastImportAt);

    assertThat(actualLocation.getWeather()).isEmpty();
  }

  @Test
  public void shouldMapToLocation() {
    LocationEntity expectedLocation = TestLocationFactory.locationEntity();

    Location actualLocation = locationEntityMapper.mapToLocation(expectedLocation);

    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::getLocationId)
        .returns(expectedLocation.getUuid(), Location::getUuid)
        .returns(expectedLocation.getLatitude(), Location::getLatitude)
        .returns(expectedLocation.getLongitude(), Location::getLongitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::getOpenWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::getLocalZipCode)
        .returns(expectedLocation.getCityName(), Location::getCityName)
        .returns(expectedLocation.getCountry(), Location::getCountry)
        .returns(expectedLocation.getCountryCode(), Location::getCountryCode)
        .returns(expectedLocation.getLastImportAt(), Location::getLastImportAt);

    assertThat(actualLocation.getWeather()).hasSize(1);
  }

  @Test
  public void shouldMapToLocationList() {
    LocationEntity expectedLocation = TestLocationFactory.locationEntity();

    List<Location> actualLocationList = locationEntityMapper.mapToLocationList(List.of(expectedLocation));

    assertThat(actualLocationList).hasSize(1);
    Location actualLocation = actualLocationList.get(0);

    assertThat(actualLocation)
        .returns(expectedLocation.getId(), Location::getLocationId)
        .returns(expectedLocation.getUuid(), Location::getUuid)
        .returns(expectedLocation.getLatitude(), Location::getLatitude)
        .returns(expectedLocation.getLongitude(), Location::getLongitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), Location::getOpenWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), Location::getLocalZipCode)
        .returns(expectedLocation.getCityName(), Location::getCityName)
        .returns(expectedLocation.getCountry(), Location::getCountry)
        .returns(expectedLocation.getCountryCode(), Location::getCountryCode)
        .returns(expectedLocation.getLastImportAt(), Location::getLastImportAt);

    assertThat(actualLocation.getWeather()).hasSize(1);
  }

}
