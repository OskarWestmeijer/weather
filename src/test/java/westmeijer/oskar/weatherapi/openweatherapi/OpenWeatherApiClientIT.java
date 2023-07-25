package westmeijer.oskar.weatherapi.openweatherapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@SpringBootTest
public class OpenWeatherApiClientIT extends IntegrationTestContainers {

  @Autowired
  private OpenWeatherApiClient apiClient;

  @Test
  public void requestApiForHelsinki() {
    Location helsinki = new Location("00100", "658225", "Helsinki", "Finland", Instant.now());
    Weather response = apiClient.requestWeather(helsinki);

    assertThat(10.00).isEqualTo(response.temperature());
  }

  @DisplayName("Ensure error request handling is covered.")
  @Test
  public void handleErrorResponses() {
    Location error = new Location("66666", "666666", "Error", "Error", Instant.now());

    assertThatThrownBy(() -> apiClient.requestWeather(error))
        .isInstanceOf(OpenWeatherApiRequestException.class)
        .hasMessageContaining("Exception during OpenWeatherApi request.");
  }


}
