package westmeijer.oskar.weatherapi.configuration;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
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
    var timeout = Duration.ofSeconds(10);

    var webClient = ApiClient.buildWebClientBuilder()
        .clientConnector(new ReactorClientHttpConnector(
            HttpClient.create()
                .responseTimeout(timeout)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) timeout.toMillis())
        ))
        .build();

    ApiClient apiClient = new ApiClient(webClient);
    apiClient.setBasePath(baseUrl);
    return new GeneratedOpenWeatherApi(apiClient);
  }

}
