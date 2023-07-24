package westmeijer.oskar.weatherapi.controller.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
public class WeatherDtoJsonTest {

  @Autowired
  private JacksonTester<WeatherDto> tester;

  @Test
  @SneakyThrows
  public void serializeToJson() {
    Instant recordedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
    WeatherDto weatherDTO = new WeatherDto(UUID.randomUUID(), 22.54d, 34, 89.12d, "1234", recordedAt);

    JsonContent<WeatherDto> json = tester.write(weatherDTO);

    assertThat(json).doesNotHaveJsonPath("id");
    assertThat(json.getJson()).isEqualTo("""
        {"temperature":%s,"humidity":%s,"windSpeed":%s,"recordedAt":"%s"}"""
        .formatted(weatherDTO.temperature(), weatherDTO.humidity(), weatherDTO.windSpeed(), weatherDTO.recordedAt()));

  }

}
