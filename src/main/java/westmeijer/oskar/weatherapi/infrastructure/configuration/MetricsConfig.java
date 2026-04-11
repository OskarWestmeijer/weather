package westmeijer.oskar.weatherapi.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

  @Bean
  public MeterRegistry init() {
    MeterRegistry meterRegistry = new SimpleMeterRegistry();
    meterRegistry.config().commonTags("service", "weather-api");
    return meterRegistry;
  }

}
