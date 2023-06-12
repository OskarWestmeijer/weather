package westmeijer.oskar.weatherapi.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This custom scheduling property is disabled for Integration Tests with Testcontainers.
 * TODO: disabled for GCP migration as well.
 * Have a look at the IntegrationTestContainers class in the test package.
 */
@EnableScheduling
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
public class SchedulingConfiguration {


}
