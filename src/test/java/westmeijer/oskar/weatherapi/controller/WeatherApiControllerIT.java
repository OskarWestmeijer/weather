package westmeijer.oskar.weatherapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApiControllerIT extends IntegrationTestContainers {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void retrievesCorrectTemperatures() throws Exception {

        mockMvc.perform(get("/api/v1/weather/23552/2022-08-27"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                        "timeFormat" : "UTC",
                        "cityName" : "Lübeck",
                        "localZipCode" : "23552",
                        "country" : "Germany",
                        "weatherData":[{"temperature":16.45,"humidity":45,"windSpeed":2.57,"recordedAt":"2022-08-27T21:56:18Z"},
                        {"temperature":17.45,"humidity":45,"windSpeed":2.57,"recordedAt":"2022-08-27T21:16:18Z"},
                        {"temperature":20.75,"humidity":45,"windSpeed": 2.57,"recordedAt":"2022-08-27T20:21:17Z"}]
                        }""",false));
    }

}
