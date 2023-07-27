package westmeijer.oskar.weatherapi.controller.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
    WeatherDto weatherDTO = new WeatherDto(22.54d, 34, 89.12d, recordedAt);

    @Language("json")
    String jsonTemplate = """
        {
          "temperature":%s,
          "humidity":%s,
          "windSpeed":%s,
          "recordedAt":"%s"
        }""";

    String expectedJson = jsonTemplate.formatted(weatherDTO.temperature(), weatherDTO.humidity(), weatherDTO.windSpeed(),
        weatherDTO.recordedAt());
    JsonContent<WeatherDto> actualJson = tester.write(weatherDTO);

    JSONAssert.assertEquals(expectedJson, actualJson.getJson(), true);
  }

}
