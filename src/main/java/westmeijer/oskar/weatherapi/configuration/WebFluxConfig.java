package westmeijer.oskar.weatherapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebFluxConfig.class);

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
