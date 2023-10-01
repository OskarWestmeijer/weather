package westmeijer.oskar.weatherapi.importjob.client.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.openapi.client.model.Main;
import westmeijer.oskar.openapi.client.model.Wind;
import westmeijer.oskar.weatherapi.importjob.client.mapper.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.service.model.Weather;

public class OpenWeatherApiMapperTest {

  private final OpenWeatherApiMapper openWeatherApiMapper = Mappers.getMapper(OpenWeatherApiMapper.class);

  @Test
  public void shouldMapSuccessfully() {
    Integer humidity = 55;
    Double windSpeed = 25.55;
    Double temperature = -10.35;
    String localZipCode = "20535";
    Integer locationId = 1;

    Main main = new Main()
        .humidity(humidity)
        .temp(temperature);
    Wind wind = new Wind()
        .speed(windSpeed);
    GeneratedOpenWeatherApiResponse response = new GeneratedOpenWeatherApiResponse()
        .main(main)
        .wind(wind);

    Weather weather = openWeatherApiMapper.map(response, localZipCode, locationId);

    assertThat(weather)
        .returns(windSpeed, Weather::windSpeed)
        .returns(humidity, Weather::humidity)
        .returns(temperature, Weather::temperature)
        .returns(localZipCode, Weather::localZipCode)
        .returns(locationId, Weather::locationId)
        .returns(UUID.class, w -> w.id().getClass())
        .returns(Instant.class, w -> w.recordedAt().getClass());
  }

}
