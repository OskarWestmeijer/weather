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
import org.testcontainers.utility.MountableFile;

@TestPropertySource(properties = "app.scheduling.enable=false")
@Testcontainers
public class IntegrationTestContainers {

    @Container
    public static final GenericContainer DATABASE = new PostgreSQLContainer("postgres:14.1")
            .withUsername("username1")
            .withPassword("password1")
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/1_database.sql", 0444),
                    "/docker-entrypoint-initdb.d/1_database.sql")
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/2_schema.sql", 0444),
                    "/docker-entrypoint-initdb.d/2_schema.sql")
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/3_data.sql", 0444),
                    "/docker-entrypoint-initdb.d/3_data.sql");

    @Container
    public static final GenericContainer<?> OPEN_WEATHER_API = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.35.0"))
            .withClasspathResourceMapping("mappings",
                    "/home/wiremock/mappings",
                    BindMode.READ_ONLY)
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/__admin/"));

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
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
