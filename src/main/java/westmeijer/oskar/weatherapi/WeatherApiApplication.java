package westmeijer.oskar.weatherapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class WeatherApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WeatherApiApplication.class, args);

        // the server and the requested cities are running in germany.
        //TimeZone.setDefault(TimeZone.getTimeZone("Germany"));
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        final Environment env = event.getApplicationContext()
                .getEnvironment();
        logger.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
    }


}
