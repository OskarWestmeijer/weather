package westmeijer.oskar.weatherapi.overview.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.WebMvcMappersTestConfig;
import westmeijer.oskar.weatherapi.overview.service.OverviewService;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;

@WebMvcTest(OverviewController.class)
@Import(WebMvcMappersTestConfig.class)
public class OverviewControllerLayerTest {

  @MockBean
  private OverviewService overviewService;

  @Autowired
  MockMvc mockMvc;

  @Test
  @SneakyThrows
  public void shouldGetOverview() {
    Overview overview = new Overview(1, "Luebeck", "GER", 15.66, 44, 10.11, Instant.now().truncatedTo(ChronoUnit.MICROS));

    given(overviewService.getOverview()).willReturn(List.of(overview));

    @Language("json")
    String expectedBody = """
        {
          "overview":  [
            {
              "locationId": 1,
              "cityName":"Luebeck",
              "countryCode":"GER",
              "temperature":15.66,
              "humidity":44,
              "windSpeed":10.11,
              "recordedAt":"%s"
            }
          ]
        }""";

    mockMvc.perform(get("/overview"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            expectedBody.formatted(overview.recordedAt())));

    then(overviewService).should().getOverview();
  }

}
