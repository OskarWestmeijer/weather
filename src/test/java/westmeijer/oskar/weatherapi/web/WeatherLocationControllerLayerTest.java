package westmeijer.oskar.weatherapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.service.WeatherService;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.*;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherLocationController.class)
public class WeatherLocationControllerLayerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void requestWeatherKnownZipCode() throws Exception {

        List<Weather> weatherData = List.of(new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, Instant.now(), 88,11.66,23552));
        when(weatherService.getLatestWeather()).thenReturn(weatherData);

        mockMvc.perform(get("/api/v1/weather/23552/24h"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "timeFormat" : "UTC",
                            "location" : "Lübeck",
                            "zipCode" : "23552",
                            "country" : "Germany",
                            "weatherData" : [{"temperature": 5.45}]
                        }"""));
    }

    @Test
    public void requestWeatherUnknownZipCode() throws Exception {
        mockMvc.perform(get("/api/v1/weather/46286/24h"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown zip code!"));
    }


}
