package westmeijer.oskar.weatherapi.weather.controller;

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
public class WeatherControllerIT extends IntegrationTestContainers {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  public void shouldReturnWeather() {
    @Language("json")
    String expectedResponse = """
        {
          "cityName" : "Lübeck",
          "localZipCode" : "23552",
          "country" : "Germany",
          "weatherData":
          [
            {"temperature":15.45,"humidity":45,"windSpeed":2.57},
            {"temperature":16.45,"humidity":45,"windSpeed":2.57},
            {"temperature":17.45,"humidity":45,"windSpeed":2.57},
            {"temperature":20.75,"humidity":45,"windSpeed": 2.57}
          ]
        }""";

    mockMvc.perform(get("/weather/23552/24h"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse, false));
  }

}
