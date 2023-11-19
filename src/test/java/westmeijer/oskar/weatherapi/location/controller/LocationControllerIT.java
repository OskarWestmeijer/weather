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
    // last import timestamps are not asserted
    @Language("json")
    String expectedResponse = """
        {
          "locations": [
            {"locationId":1,"localZipCode":"23552","cityName":"Lübeck","country":"Germany","countryCode":"GER"},
            {"locationId":2,"localZipCode":"20095","cityName":"Hamburg","country":"Germany","countryCode":"GER"},
            {"locationId":3,"localZipCode":"46286","cityName":"Dorsten","country":"Germany","countryCode":"GER"},
            {"locationId":4,"localZipCode":"00100","cityName":"Helsinki","country":"Finland","countryCode":"FIN"},
            {"locationId":5,"localZipCode":"36100","cityName":"Kangasala","country":"Finland","countryCode":"FIN"}
          ]
        }""";

    mockMvc.perform(get("/locations"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse, false));
  }


}
