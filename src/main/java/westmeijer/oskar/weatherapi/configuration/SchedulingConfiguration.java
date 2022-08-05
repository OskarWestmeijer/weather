package westmeijer.oskar.weatherapi.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The scheduling property is disable for Integration Tests with Testcontainers.
 * Have a look at the SystemTest class in the test package.
 */
@EnableScheduling
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
public class SchedulingConfiguration {


}
