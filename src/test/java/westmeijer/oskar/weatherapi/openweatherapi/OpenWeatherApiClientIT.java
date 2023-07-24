package westmeijer.oskar.weatherapi.openweatherapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Slf4j
@SpringBootTest
public class OpenWeatherApiClientIT extends IntegrationTestContainers {

  @Autowired
  private OpenWeatherApiClient apiClient;

  @Test
  public void requestApiForHelsinki() {
    Location helsinki = new Location("00100", "658225", "Helsinki", "Finland", Instant.now(), Instant.now());
    Weather response = apiClient.requestWeather(helsinki);

    assertThat(10.00).isEqualTo(response.temperature());
  }

  @DisplayName("Ensure error request handling is covered.")
  @Test
  public void handleErrorResponses() {
    Location error = new Location("66666", "666666", "Error", "Error", Instant.now(), Instant.now());

    OpenWeatherApiRequestException thrown = Assertions.assertThrows(OpenWeatherApiRequestException.class, () -> {
      apiClient.requestWeather(error);
    });

    assertThat(OpenWeatherApiRequestException.class).isEqualTo(thrown.getClass());
    assertThat("Exception during OpenWeatherApi request.").isEqualTo(thrown.getMessage());
  }


}
