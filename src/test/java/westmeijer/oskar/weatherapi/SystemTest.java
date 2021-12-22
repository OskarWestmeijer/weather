package westmeijer.oskar.weatherapi;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;


@SpringBootTest
@TestPropertySource(properties = "app.scheduling.enable=false")
@ContextConfiguration(initializers = SystemTest.Initializer.class)
public class SystemTest {

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://" + DATABASE.getContainerIpAddress() + ':' +
                            DATABASE.getMappedPort(5432) + "/weather",
                    "spring.datasource.username=username1"
            );
            values.applyTo(applicationContext);
        }

    }

    private static final GenericContainer DATABASE =
            new GenericContainer("postgres:14.1")
                    .withExposedPorts(5432)
                    .withEnv("POSTGRES_USER", "username1")
                    .withEnv("POSTGRES_PASSWORD", "password1")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("db/1_database.sql", 0444),
                            "/docker-entrypoint-initdb.d/1_database.sql")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("db/2_schema.sql", 0444),
                            "/docker-entrypoint-initdb.d/2_schema.sql")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("db/3_data.sql", 0444),
                            "/docker-entrypoint-initdb.d/3_data.sql");

    static {
        DATABASE.start();
    }
}
