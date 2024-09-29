package westmeijer.oskar.weatherapi.weather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.SneakyThrows;
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

    String expectedResponse = """
        {
          "locationId" : 1,
          "cityName" : "Lübeck",
          "country" : "Germany",
          "weatherData":
          [
            {"id":"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15","temperature":15.45,"humidity":45,"windSpeed":2.57},
            {"id":"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16","temperature":16.45,"humidity":45,"windSpeed":2.57},
            {"id":"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a17","temperature":17.45,"humidity":45,"windSpeed":2.57}
          ]
        }""";

    mockMvc.perform(get("/weather?locationId=1&from=%s&limit=3".formatted(Instant.now().minus(1, ChronoUnit.DAYS))))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedResponse, false));
  }

}
