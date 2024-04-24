package westmeijer.oskar.weatherapi.overview.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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
    Weather expectedWeather = expectedLocation.weather().getFirst();

    Overview actualOverview = overviewMapper.mapToOverview(expectedLocation);

    assertThat(actualOverview)
        .returns(expectedLocation.locationId(), Overview::locationId)
        .returns(expectedLocation.countryCode(), Overview::countryCode)
        .returns(expectedLocation.cityName(), Overview::cityName)
        .returns(expectedWeather.temperature(), Overview::temperature)
        .returns(expectedWeather.humidity(), Overview::humidity)
        .returns(expectedWeather.windSpeed(), Overview::windSpeed)
        .returns(expectedWeather.recordedAt(), Overview::recordedAt);
  }

  @Test
  void shouldMapToOverviewList() {
    Location expectedLocation = TestLocationFactory.location();

    List<Overview> actualOverviewList = overviewMapper.mapToOverviewList(List.of(expectedLocation));

    assertThat(actualOverviewList)
        .hasSize(1)
        .first()
        .isNotNull();
  }

  @Test
  void throwsNpeOnNoWeather() {
    Location expectedLocation = TestLocationFactory.locationWithoutWeather();

    thenThrownBy(() -> overviewMapper.mapToOverviewList(List.of(expectedLocation)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("requires exactly one weather element");
  }

  @Test
  void throwsIllegalOnTooManyWeather() {
    Location expectedLocation = new Location(1,
        UUID.randomUUID(),
        "1234",
        "5678",
        "Luebeck",
        "Germany",
        "GER",
        "51.659088",
        "6.966170",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        List.of(TestWeatherFactory.weather(), TestWeatherFactory.weather()));

    thenThrownBy(() -> overviewMapper.mapToOverviewList(List.of(expectedLocation)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("requires exactly one weather element");
  }

  @Test
  void throwsNpeOnMissingLocation() {
    thenThrownBy(() -> overviewMapper.mapToOverview(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("location is required");
  }

}