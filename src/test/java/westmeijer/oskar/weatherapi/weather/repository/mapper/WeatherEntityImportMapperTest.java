package westmeijer.oskar.weatherapi.weather.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WeatherEntityImportMapperImpl.class
})
public class WeatherEntityImportMapperTest {

  @Autowired
  private WeatherEntityImportMapper weatherEntityImportMapper;

  @Test
  void shouldMapToLocationEntity() {

    Location expectedLocation = TestLocationFactory.location();
    LocationEntity actualLocation = weatherEntityImportMapper.mapToLocationEntity(expectedLocation);

    assertThat(actualLocation)
        .returns(expectedLocation.getLocationId(), LocationEntity::getId)
        .returns(expectedLocation.getUuid(), LocationEntity::getUuid)
        .returns(expectedLocation.getLatitude(), LocationEntity::getLatitude)
        .returns(expectedLocation.getLongitude(), LocationEntity::getLongitude)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), LocationEntity::getOpenWeatherApiLocationCode)
        .returns(expectedLocation.getLocalZipCode(), LocationEntity::getLocalZipCode)
        .returns(expectedLocation.getCityName(), LocationEntity::getCityName)
        .returns(expectedLocation.getCountry(), LocationEntity::getCountry)
        .returns(expectedLocation.getCountryCode(), LocationEntity::getCountryCode)
        .returns(expectedLocation.getLastImportAt(), LocationEntity::getLastImportAt);

    assertThat(actualLocation.getModifiedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.SECONDS));
    assertThat(actualLocation.getWeather()).isEmpty();
  }

  @Test
  void shouldMapToWeatherEntity() {
    Weather expectedWeather = TestWeatherFactory.weather();

    WeatherEntity actualWeather = weatherEntityImportMapper.mapToWeatherEntity(expectedWeather);

    assertThat(actualWeather)
        .returns(expectedWeather.getId(), WeatherEntity::getId)
        .returns(expectedWeather.getWindSpeed(), WeatherEntity::getWindSpeed)
        .returns(expectedWeather.getTemperature(), WeatherEntity::getTemperature)
        .returns(expectedWeather.getHumidity(), WeatherEntity::getHumidity)
        .returns(expectedWeather.getRecordedAt(), WeatherEntity::getRecordedAt);
    assertThat(actualWeather.getModifiedAt()).isCloseTo(Instant.now(), within(1, ChronoUnit.SECONDS));

    assertThat(actualWeather.getLocation()).isNotNull();
  }

}
