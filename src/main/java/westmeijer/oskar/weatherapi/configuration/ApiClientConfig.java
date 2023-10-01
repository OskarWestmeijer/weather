package westmeijer.oskar.weatherapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import westmeijer.oskar.weatherapi.openapi.client.ApiClient;
import westmeijer.oskar.weatherapi.openapi.client.api.GeneratedOpenWeatherApi;

@Configuration
@Slf4j
public class ApiClientConfig {

  private final String baseUrl;

  public ApiClientConfig(@Value("${openweatherapi.baseUrl}") String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Bean
  public GeneratedOpenWeatherApi generatedOpenWeatherApi() {
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(baseUrl);
    return new GeneratedOpenWeatherApi(apiClient);
  }

}
