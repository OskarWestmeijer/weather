package westmeijer.oskar.weatherapi.weather.controller.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import westmeijer.oskar.weatherapi.openapi.server.model.WeatherDto;

@JsonTest
public class WeatherDtoJsonTest {

  @Autowired
  private JacksonTester<WeatherDto> tester;

  @Test
  @SneakyThrows
  public void serializeToJson() {
    Instant recordedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
    UUID uuid = UUID.randomUUID();
    WeatherDto weatherDTO = new WeatherDto(uuid, 22.54d, 34, 89.12d, recordedAt);

    String jsonTemplate = """
        {
          "id":"%s",
          "temperature":%s,
          "humidity":%d,
          "windSpeed":%s,
          "recordedAt":"%s"
        }""";

    String expectedJson = jsonTemplate.formatted(weatherDTO.getId(), weatherDTO.getTemperature(), weatherDTO.getHumidity(),
        weatherDTO.getWindSpeed(),
        weatherDTO.getRecordedAt());
    JsonContent<WeatherDto> actualJson = tester.write(weatherDTO);

    JSONAssert.assertEquals(expectedJson, actualJson.getJson(), true);
  }

}
