package westmeijer.oskar.weatherapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebFluxConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebFluxConfig.class);


    private final String baseUrl;

    public WebFluxConfig(@Value("${openweatherapi.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    @Scope("singleton")
    public WebClient getWebClient() {
        logger.info(baseUrl);
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
