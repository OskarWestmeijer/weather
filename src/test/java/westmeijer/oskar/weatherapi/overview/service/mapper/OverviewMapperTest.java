package westmeijer.oskar.weatherapi.overview.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    OverviewMapperImpl.class,
})
public class OverviewMapperTest {

  @Autowired
  private OverviewMapper overviewMapper;

  @Test
  void shouldMapToOverview() {
    Location expectedLocation = TestLocationFactory.location();
    Weather expectedWeather = expectedLocation.getWeather().get(0);

    Overview actualOverview = overviewMapper.mapToOverview(expectedLocation);

    assertThat(actualOverview)
        .returns(expectedLocation.getLocationId(), Overview::locationId)
        .returns(expectedLocation.getCountryCode(), Overview::countryCode)
        .returns(expectedLocation.getCityName(), Overview::cityName)
        .returns(expectedWeather.getTemperature(), Overview::temperature)
        .returns(expectedWeather.getHumidity(), Overview::humidity)
        .returns(expectedWeather.getWindSpeed(), Overview::windSpeed)
        .returns(expectedWeather.getRecordedAt(), Overview::recordedAt);
  }

  @Test
  void shouldMapToOverviewList() {
    Location expectedLocation = TestLocationFactory.location();

    List<Overview> actualOverviewList = overviewMapper.mapToOverviewList(List.of(expectedLocation));

    assertThat(actualOverviewList).hasSize(1);
    assertThat(actualOverviewList.get(0)).isNotNull();
  }

  @Test
  void throwsNpeOnNoWeather() {
    Location expectedLocation = TestLocationFactory.locationWithoutWeather();

    assertThatThrownBy(() -> overviewMapper.mapToOverviewList(List.of(expectedLocation)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("requires exactly one weather element");
  }

  @Test
  void throwsIllegalOnTooManyWeather() {
    Location expectedLocation = TestLocationFactory.locationWithoutWeather();
    expectedLocation.addWeather(TestWeatherFactory.weather());
    expectedLocation.addWeather(TestWeatherFactory.weather());

    assertThatThrownBy(() -> overviewMapper.mapToOverviewList(List.of(expectedLocation)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("requires exactly one weather element");
  }

  @Test
  void throwsNpeOnMissingLocation() {
    assertThatThrownBy(() -> overviewMapper.mapToOverview(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location is required");
  }

  @Test
  void throwsNpeOnMissingWeather() {
    Location location = TestLocationFactory.locationWithoutWeather();
    location.setWeather(null);

    assertThatThrownBy(() -> overviewMapper.mapToOverview(location))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("weather is required");
  }



}