package westmeijer.oskar.weatherapi.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.openapi.client.ApiClient;
import westmeijer.oskar.weatherapi.openapi.client.api.GeneratedOpenWeatherApi;

@ExtendWith(MockitoExtension.class)
public class ApiClientConfigTest {


  @Test
  void generatedOpenWeatherApi_shouldHaveCorrectBaseUrl() {
    String baseUrl = "https://test-base-url.com";
    ApiClientConfig apiClientConfig = new ApiClientConfig(baseUrl);

    GeneratedOpenWeatherApi generatedOpenWeatherApi = apiClientConfig.generatedOpenWeatherApi();
    assertThat(generatedOpenWeatherApi).isNotNull();

    ApiClient apiClient = generatedOpenWeatherApi.getApiClient();
    assertThat(apiClient).isNotNull();
    assertThat(baseUrl).isEqualTo(apiClient.getBasePath());
  }

}
