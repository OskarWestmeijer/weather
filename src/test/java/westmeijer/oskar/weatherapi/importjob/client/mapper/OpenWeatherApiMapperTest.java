package westmeijer.oskar.weatherapi.importjob.client.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.openapi.client.model.Main;
import westmeijer.oskar.weatherapi.openapi.client.model.Wind;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public class OpenWeatherApiMapperTest {

  private final OpenWeatherApiMapper openWeatherApiMapper = Mappers.getMapper(OpenWeatherApiMapper.class);

  @Test
  public void shouldMapSuccessfully() {
    Integer humidity = 55;
    Double windSpeed = 25.55;
    Double temperature = -10.35;
    Location location = TestLocationFactory.location();

    Main main = new Main()
        .humidity(humidity)
        .temp(temperature);
    Wind wind = new Wind()
        .speed(windSpeed);
    GeneratedOpenWeatherApiResponse response = new GeneratedOpenWeatherApiResponse()
        .main(main)
        .wind(wind);

    Weather weather = openWeatherApiMapper.map(response, location);

    assertThat(weather)
        .returns(windSpeed, Weather::windSpeed)
        .returns(humidity, Weather::humidity)
        .returns(temperature, Weather::temperature)
        .returns(location, Weather::location)
        .returns(UUID.class, w -> w.id().getClass())
        .returns(Instant.class, w -> w.recordedAt().getClass());
  }

}
