package westmeijer.oskar.weatherapi.dal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.dal.database.WeatherRepository;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeatherRepositoryTest extends IntegrationTestContainers {
    @Autowired
    private WeatherRepository weatherRepository;


    @Test
    public void fetchData() {
        List<Weather> weatherData = weatherRepository.getLatestEntries();

        Assertions.assertEquals(2, weatherData.size());
        Assertions.assertEquals(10.45, weatherData.get(0).getTemperature());
    }
}
