package westmeijer.oskar.weatherapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.repository.model.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import westmeijer.oskar.weatherapi.repository.jpa.WeatherRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeatherRepositoryTest extends IntegrationTestContainers {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void fetchData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.from(formatter.parse("2022-08-28"));
        Instant start = localDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant end = start.plus(1L, ChronoUnit.DAYS);

        List<Weather> weatherData = weatherRepository.getSpecificDay("23552", start, end);

        Assertions.assertEquals(5, weatherData.size());
        Assertions.assertEquals(12.45, weatherData.get(0).getTemperature());
    }

}
