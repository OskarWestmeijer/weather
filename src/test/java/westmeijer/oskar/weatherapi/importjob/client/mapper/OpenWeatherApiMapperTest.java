package westmeijer.oskar.weatherapi.importjob.client.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.openapi.client.model.Main;
import westmeijer.oskar.weatherapi.openapi.client.model.Wind;
import westmeijer.oskar.weatherapi.overview.service.mapper.OverviewMapperImpl;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    OpenWeatherApiMapperImpl.class,
})
public class OpenWeatherApiMapperTest {

  @Autowired
  private OpenWeatherApiMapper openWeatherApiMapper;

  @Test
  public void shouldMapToWeather() {
    Integer humidity = 55;
    Double windSpeed = 25.55;
    Double temperature = -10.35;
    Location location = TestLocationFactory.locationWithoutWeather();

    Main main = new Main()
        .humidity(humidity)
        .temp(temperature);
    Wind wind = new Wind()
        .speed(windSpeed);
    GeneratedOpenWeatherApiResponse response = new GeneratedOpenWeatherApiResponse()
        .main(main)
        .wind(wind);

    Weather actualWeather = openWeatherApiMapper.mapToWeather(response, location);

    assertThat(actualWeather)
        .returns(windSpeed, Weather::getWindSpeed)
        .returns(humidity, Weather::getHumidity)
        .returns(temperature, Weather::getTemperature)
        .returns(location, Weather::getLocation)
        .returns(UUID.class, w -> w.getId().getClass())
        .returns(Instant.class, w -> w.getRecordedAt().getClass());
  }

  @Test
  void shouldMapBindWeatherToLocation() {
    Location l = TestLocationFactory.locationWithoutWeather();
    Weather w = TestWeatherFactory.weatherWithoutLocation();

    openWeatherApiMapper.bindWeatherToLocation(w, l);

    assertThat(l.getWeather()).isEqualTo(List.of(w));
    assertThat(w.getLocation()).isEqualTo(l);
  }

  @Test
  void throwsNpeOnMissingTemp() {
    Location l = TestLocationFactory.locationWithoutWeather();
    Weather w = TestWeatherFactory.weatherWithoutLocation();
    w.setTemperature(null);

    assertThatThrownBy(() -> openWeatherApiMapper.bindWeatherToLocation(w, l))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("temperature is required");

    assertThat(l.getWeather()).isEmpty();
    assertThat(w.getLocation()).isNull();
  }

  @Test
  void throwsNpeOnMissingHumid() {
    Location l = TestLocationFactory.locationWithoutWeather();
    Weather w = TestWeatherFactory.weatherWithoutLocation();
    w.setHumidity(null);

    assertThatThrownBy(() -> openWeatherApiMapper.bindWeatherToLocation(w, l))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("humidity is required");

    assertThat(l.getWeather()).isEmpty();
    assertThat(w.getLocation()).isNull();
  }

  @Test
  void throwsNpeOnMissingWind() {
    Location l = TestLocationFactory.locationWithoutWeather();
    Weather w = TestWeatherFactory.weatherWithoutLocation();
    w.setWindSpeed(null);

    assertThatThrownBy(() -> openWeatherApiMapper.bindWeatherToLocation(w, l))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("windSpeed is required");

    assertThat(l.getWeather()).isEmpty();
    assertThat(w.getLocation()).isNull();
  }

}
