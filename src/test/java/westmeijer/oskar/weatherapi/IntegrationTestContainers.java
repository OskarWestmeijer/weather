package westmeijer.oskar.weatherapi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
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


    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        StringBuilder builder = new StringBuilder("jdbc:postgresql://")
                .append(DATABASE.getHost())
                .append(":")
                .append(DATABASE.getFirstMappedPort())
                .append("/weather");
        registry.add("spring.datasource.url", builder::toString);
    }

}
