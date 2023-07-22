package westmeijer.oskar.weatherapi.repository.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeatherJpaRepositoryTest extends IntegrationTestContainers {

  @Autowired
  private WeatherJpaRepository weatherJpaRepository;

  @Test
  public void fetchWeatherForSpecificDay() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.from(formatter.parse("2022-08-28"));
    Instant start = localDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = start.plus(1L, ChronoUnit.DAYS);

    List<WeatherEntity> weatherEntityData = weatherJpaRepository.getSpecificDay("23552", start, end);

    Assertions.assertEquals(5, weatherEntityData.size());
    Assertions.assertEquals(12.45, weatherEntityData.get(0).getTemperature());
  }

}
