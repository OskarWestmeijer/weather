package westmeijer.oskar.weatherapi.importjob.client.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
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
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.openapi.client.model.Main;
import westmeijer.oskar.weatherapi.openapi.client.model.Wind;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    OpenWeatherApiMapperImpl.class,
})
public class OpenWeatherApiMapperTest {

  @Autowired
  private OpenWeatherApiMapper openWeatherApiMapper;

  @Test
  public void shouldMapToLocation() {
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

    Location actualLocation = openWeatherApiMapper.mapToLocation(response, location);

    assertThat(actualLocation)
        .returns(actualLocation.locationId(), Location::locationId);

    assertThat(actualLocation.weather()).isNotEmpty();
    // TODO: add more conditions
  }

}
