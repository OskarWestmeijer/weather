package westmeijer.oskar.weatherapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.model.WeatherDTOBuilder;
import westmeijer.oskar.weatherapi.service.WeatherService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerLayerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void requestWeatherKnownZipCode() throws Exception {
        LocalDateTime instant = LocalDateTime.of(2022, Month.APRIL, 1, 12, 10, 10);

        when(weatherService.getWeather()).thenReturn(List.of(
                new WeatherDTOBuilder().setId(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"))
                        .setTemperature(5L).setTimestmap(instant).createWeatherDTO()));

        mockMvc.perform(get("/api/weather/23552"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{"id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                        "temperature": 5,
                        "timestamp":"2022-04-01T12:10:10"
                        }]"""));
    }

    @Test
    public void requestWeatherUnknownZipCode() throws Exception {
        mockMvc.perform(get("/api/weather/46286"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown zip code!"));
    }


}
