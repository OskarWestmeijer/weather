package westmeijer.oskar.weatherapi.weather.repository.jpa;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.Instant;
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
public class WeatherJpaRepositoryIT extends IntegrationTestContainers {

  @Autowired
  private WeatherJpaRepository weatherJpaRepository;

  @Test
  void shouldGetWeather() {
    WeatherEntity expectedWeather = TestWeatherFactory.weatherEntityWithoutLocation();
    LocationEntity expectedLocation = TestLocationFactory.locationEntityWithoutWeather();
    expectedLocation.setId(9999);
    expectedLocation.addWeather(expectedWeather);

    weatherJpaRepository.saveAndFlush(expectedWeather);

    var actualWeatherList = weatherJpaRepository.getWeather(9999, Instant.EPOCH, 10);

    then(actualWeatherList).hasSize(1)
        .first()
        .returns(expectedWeather.getId(), WeatherEntity::getId)
        .returns(expectedWeather.getTemperature(), WeatherEntity::getTemperature)
        .returns(expectedWeather.getHumidity(), WeatherEntity::getHumidity)
        .returns(expectedWeather.getWindSpeed(), WeatherEntity::getWindSpeed)
        .returns(expectedWeather.getRecordedAt(), WeatherEntity::getRecordedAt)
        .returns(expectedWeather.getModifiedAt(), WeatherEntity::getModifiedAt);

    then(actualWeatherList.getFirst().getLocation())
        .isNotNull()
        .returns(expectedLocation.getId(), LocationEntity::getId)
        .returns(expectedLocation.getLatitude(), LocationEntity::getLatitude)
        .returns(expectedLocation.getLongitude(), LocationEntity::getLongitude)
        .returns(expectedLocation.getCityName(), LocationEntity::getCityName)
        .returns(expectedLocation.getCountryCode(), LocationEntity::getCountryCode)
        .returns(expectedLocation.getCountry(), LocationEntity::getCountry)
        .returns(expectedLocation.getLocalZipCode(), LocationEntity::getLocalZipCode)
        .returns(expectedLocation.getLastImportAt(), LocationEntity::getLastImportAt)
        .returns(expectedLocation.getOpenWeatherApiLocationCode(), LocationEntity::getOpenWeatherApiLocationCode);
  }

  @Test
  void shouldGetTotalCount() {
    WeatherEntity expectedWeather = TestWeatherFactory.weatherEntityWithoutLocation();
    LocationEntity expectedLocation = TestLocationFactory.locationEntityWithoutWeather();
    expectedLocation.setId(9999);
    expectedLocation.addWeather(expectedWeather);

    weatherJpaRepository.saveAndFlush(expectedWeather);

    var actualTotalCount = weatherJpaRepository.getTotalCount(9999, Instant.EPOCH);
    then(actualTotalCount).isEqualTo(1);
  }

}
