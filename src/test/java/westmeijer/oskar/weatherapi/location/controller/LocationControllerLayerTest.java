package westmeijer.oskar.weatherapi.location.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.WebMvcMappersTestConfig;

@WebMvcTest(LocationController.class)
@Import(WebMvcMappersTestConfig.class)
public class LocationControllerLayerTest {

  @MockBean
  private LocationService locationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  public void shouldReturnLocations() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);

    Location luebeck = new Location(
        1,
        UUID.randomUUID(),
        "23552",
        "2875601",
        "Lübeck",
        "Germany",
        "GER",
        now
    );
    Location hamburg = new Location(
        2,
        UUID.randomUUID(),
        "20095",
        "2911298",
        "Hamburg",
        "Germany",
        "GER",
        now
    );
    given(locationService.getAll()).willReturn(List.of(luebeck, hamburg));

    @Language("json")
    String expectedBody = """
        {
          "locations":  [
            {
              "uuid":"%s",
              "localZipCode":"23552",
              "locationCode":"2875601",
              "cityName":"Lübeck",
              "country":"Germany",
              "countryCode":"GER",
              "lastImportAt":"%s"
            },
            {
              "uuid":"%s",
              "localZipCode":"20095",
              "locationCode":"2911298",
              "cityName":"Hamburg",
              "country":"Germany",
              "countryCode":"GER",
              "lastImportAt":"%s"
            }
          ]
        }""";

    mockMvc.perform(get("/locations"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            expectedBody.formatted(luebeck.uuid(), luebeck.lastImportAt(), hamburg.uuid(), hamburg.lastImportAt())));

    then(locationService).should().getAll();
  }

}