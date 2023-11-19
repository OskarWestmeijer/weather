package westmeijer.oskar.weatherapi.weather.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeatherJpaRepositoryTest extends IntegrationTestContainers {

  @Autowired
  private WeatherJpaRepository weatherJpaRepository;

  @Test
  public void shouldGetLatest() {

    WeatherEntity expectedWeather = TestWeatherFactory.weatherEntityWithoutLocation();
    LocationEntity expectedLocation = TestLocationFactory.locationEntityWithoutWeather();
    expectedLocation.setId(9999);
    expectedLocation.addWeather(expectedWeather);

    weatherJpaRepository.saveAndFlush(expectedWeather);

    WeatherEntity actualWeather = weatherJpaRepository.getLatest(9999);

    assertThat(actualWeather).isNotNull();
    assertThat(expectedWeather)
        .returns(actualWeather.getId(), WeatherEntity::getId)
        .returns(actualWeather.getTemperature(), WeatherEntity::getTemperature)
        .returns(actualWeather.getHumidity(), WeatherEntity::getHumidity)
        .returns(actualWeather.getWindSpeed(), WeatherEntity::getWindSpeed)
        .returns(actualWeather.getRecordedAt(), WeatherEntity::getRecordedAt)
        .returns(actualWeather.getModifiedAt(), WeatherEntity::getModifiedAt);

    LocationEntity actualLocation = actualWeather.getLocation();
    assertThat(actualLocation).isNotNull();
    assertThat(actualLocation)
        .returns(expectedLocation.getId(), LocationEntity::getId)
        .returns(actualLocation.getLatitude(), LocationEntity::getLatitude)
        .returns(actualLocation.getLongitude(), LocationEntity::getLongitude)
        .returns(actualLocation.getCityName(), LocationEntity::getCityName)
        .returns(actualLocation.getCountryCode(), LocationEntity::getCountryCode)
        .returns(actualLocation.getCountry(), LocationEntity::getCountry)
        .returns(actualLocation.getLocalZipCode(), LocationEntity::getLocalZipCode)
        .returns(actualLocation.getLastImportAt(), LocationEntity::getLastImportAt)
        .returns(actualLocation.getOpenWeatherApiLocationCode(), LocationEntity::getOpenWeatherApiLocationCode);
  }

}
