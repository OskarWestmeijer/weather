package westmeijer.oskar.weatherapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This custom scheduling property is disabled for Integration Tests with Testcontainers. Have a look at the IntegrationTestContainers class
 * in the test package.
 */
@EnableScheduling
@ConditionalOnProperty(
    value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
public class SchedulingConfig {


  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.setPoolSize(1);
    scheduler.setThreadNamePrefix("ScheduledWeatherImportJob-");
    scheduler.setErrorHandler(new SchedulingErrorHandler());
    return scheduler;
  }

  @Slf4j
  static class SchedulingErrorHandler implements org.springframework.util.ErrorHandler {

    @Override
    public void handleError(Throwable t) {
      log.error("Error during scheduled task.", t);
    }
  }

}
