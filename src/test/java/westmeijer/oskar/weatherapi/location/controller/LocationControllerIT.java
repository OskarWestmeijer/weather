package westmeijer.oskar.weatherapi.location.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerIT extends IntegrationTestContainers {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  public void shouldReturnLocations() {
    @Language("json")
    String expectedResponse = """
        [
          {"localZipCode":"23552","locationCode":"2875601","cityName":"Lübeck","country":"Germany"},
          {"localZipCode":"20095","locationCode":"2911298","cityName":"Hamburg","country":"Germany"},
          {"localZipCode":"46286","locationCode":"2935530","cityName":"Dorsten","country":"Germany"},
          {"localZipCode":"00100","locationCode":"658225","cityName":"Helsinki","country":"Finland"},
          {"localZipCode":"36100","locationCode":"654440","cityName":"Kangasala","country":"Finland"}
        ]
        """;

    mockMvc.perform(get("/locations"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse, false));
  }


}
