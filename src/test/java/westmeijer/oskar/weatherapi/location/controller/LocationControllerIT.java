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
            {"uuid":"0abd3e97-0764-49f9-9ff7-1efcf05a78f3","localZipCode":"23552","locationCode":"2875601","cityName":"Lübeck","country":"Germany","countryCode":"GER"},
            {"uuid":"88db2e56-7a58-4e08-adc6-89e62ba2bdea","localZipCode":"20095","locationCode":"2911298","cityName":"Hamburg","country":"Germany","countryCode":"GER"},
            {"uuid":"8766ea5a-104f-4495-abca-b13eea319080","localZipCode":"46286","locationCode":"2935530","cityName":"Dorsten","country":"Germany","countryCode":"GER"},
            {"uuid":"9032fa95-ed46-4cb3-b47d-5a27d7bf5613","localZipCode":"00100","locationCode":"658225","cityName":"Helsinki","country":"Finland","countryCode":"FIN"},
            {"uuid":"345764db-0a14-429c-bdd6-c6b53a9447c3","localZipCode":"36100","locationCode":"654440","cityName":"Kangasala","country":"Finland","countryCode":"FIN"}
          ]
        }""";

    mockMvc.perform(get("/locations"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse, false));
  }


}
