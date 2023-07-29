package westmeijer.oskar.weatherapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebFluxConfig {

  private final String baseUrl;

  public WebFluxConfig(@Value("${openweatherapi.baseUrl}") String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Bean
  @Scope("singleton")
  public WebClient getWebClient() {
    log.info(baseUrl);
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

}
