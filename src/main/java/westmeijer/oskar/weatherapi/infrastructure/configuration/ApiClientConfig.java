package westmeijer.oskar.weatherapi.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import westmeijer.oskar.weatherapi.openapi.client.ApiClient;
import westmeijer.oskar.weatherapi.openapi.client.api.GeneratedOpenWeatherApi;

@Configuration
public class ApiClientConfig {

  private final String baseUrl;

  public ApiClientConfig(@Value("${openweatherapi.baseUrl}") String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Bean
  RestClient externalWeatherApirestClient() {
    var timeout = Duration.ofSeconds(10);

    var javaHttpClient = new JdkClientHttpRequestFactory();
    javaHttpClient.setReadTimeout(timeout);

    return RestClient.builder()
        .requestFactory(javaHttpClient)
        .build();
  }

  @Bean
  public GeneratedOpenWeatherApi generatedOpenWeatherApi(RestClient externalWeatherApirestClient) {
    var apiClient = new ApiClient(externalWeatherApirestClient);
    apiClient.setBasePath(baseUrl);
    return new GeneratedOpenWeatherApi(apiClient);
  }

}
