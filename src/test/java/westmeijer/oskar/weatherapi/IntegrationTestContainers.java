package westmeijer.oskar.weatherapi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestPropertySource(properties = "app.scheduling.enable=false")
@Testcontainers
public class IntegrationTestContainers {

    @Container
    private static final GenericContainer<?> DATABASE = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"))
            .withUsername("username1")
            .withPassword("password1")
            .withDatabaseName("weather");

    @Container
    private static final GenericContainer<?> OPEN_WEATHER_API = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.35.0"))
            .withClasspathResourceMapping("wiremock/mappings",
                    "/home/wiremock/mappings",
                    BindMode.READ_ONLY)
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/data/2.5/weather?id=658225&units=metric&appid=1234random").forStatusCode(200));

    @DynamicPropertySource
    private static void registerContainers(DynamicPropertyRegistry registry) {
        // testcontainers uses random ports on startup, we need to apply them to the spring boot application context
        StringBuilder postgresBuilder = new StringBuilder("jdbc:postgresql://")
                .append(DATABASE.getHost())
                .append(":")
                .append(DATABASE.getFirstMappedPort())
                .append("/weather");
        registry.add("spring.datasource.url", postgresBuilder::toString);

        StringBuilder wiremockBuilder = new StringBuilder("http://")
                .append(OPEN_WEATHER_API.getHost())
                .append(":")
                .append(OPEN_WEATHER_API.getFirstMappedPort());
        registry.add("openweatherapi.baseUrl", wiremockBuilder::toString);
    }

}
