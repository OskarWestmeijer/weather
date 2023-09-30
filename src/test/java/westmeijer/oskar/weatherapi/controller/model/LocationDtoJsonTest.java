package westmeijer.oskar.weatherapi.controller.model;

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
import westmeijer.oskar.openapi.server.model.LocationDto;

@JsonTest
public class LocationDtoJsonTest {


  @Autowired
  private JacksonTester<LocationDto> tester;

  @Test
  @SneakyThrows
  public void serializeToJson() {
    Instant recordedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
    LocationDto locationDto = new LocationDto()
        .localZipCode("23552")
        .locationCode("2875601")
        .cityName("Lübeck")
        .country("Germany")
        .lastImportAt(recordedAt);

    @Language("json")
    String jsonTemplate = """
        {
          "localZipCode":"23552",
          "locationCode":"2875601",
          "cityName":"Lübeck",
          "country":"Germany",
          "lastImportAt":"%s"
        }""";

    String expectedJson = jsonTemplate.formatted(locationDto.getLastImportAt());
    JsonContent<LocationDto> actualJson = tester.write(locationDto);

    JSONAssert.assertEquals(expectedJson, actualJson.getJson(), true);
  }

}
