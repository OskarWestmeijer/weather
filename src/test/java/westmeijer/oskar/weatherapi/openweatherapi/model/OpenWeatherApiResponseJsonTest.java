package westmeijer.oskar.weatherapi.openweatherapi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;


@JsonTest
public class OpenWeatherApiResponseJsonTest {

  @Autowired
  private JacksonTester<OpenWeatherApiResponse> tester;

  @Value("classpath:jsontest/openweatherapi/valid_weather_response.json")
  private Resource validJsonResponse;

  @Value("classpath:jsontest/openweatherapi/invalid_weather_response_humidity_missing.json")
  private Resource invalidJsonResponse;


  @Test
  @SneakyThrows
  public void deserializeToObject() {

    OpenWeatherApiResponse response = tester.read(validJsonResponse).getObject();

    assertThat(99.53d).isEqualTo(response.main().temperature());
    assertThat(2.57d).isEqualTo(response.wind().windSpeed());
    assertThat(85).isEqualTo(response.main().humidity());
  }

  @Test
  @SneakyThrows
  public void deserializeThrowsExceptionOnMissingHumidity() {
    assertThatThrownBy(() -> tester.read(invalidJsonResponse).getObject())
        .isInstanceOf(MismatchedInputException.class);
  }

}
