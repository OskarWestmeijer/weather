package westmeijer.oskar.weatherapi.importjob.model;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;


@JsonTest
public class OpenWeatherApiResponseJsonTest {

  @Autowired
  private JacksonTester<GeneratedOpenWeatherApiResponse> tester;

  @Value("classpath:jsontest/openweatherapi/valid_weather_response.json")
  private Resource validJsonResponse;

  @Value("classpath:jsontest/openweatherapi/invalid_weather_response_humidity_missing.json")
  private Resource invalidJsonResponse;


  @Test
  @SneakyThrows
  public void deserializeToObject() {

    GeneratedOpenWeatherApiResponse response = tester.read(validJsonResponse).getObject();

    assertThat(99.53d).isEqualTo(response.getMain().getTemp());
    assertThat(2.57d).isEqualTo(response.getWind().getSpeed());
    assertThat(85).isEqualTo(response.getMain().getHumidity());
  }

}
