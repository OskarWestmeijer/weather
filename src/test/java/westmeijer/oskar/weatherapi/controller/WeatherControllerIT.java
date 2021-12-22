package westmeijer.oskar.weatherapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.SystemTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class WeatherControllerIT extends SystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void retrievesCorrectTemperatures() throws Exception {

        mockMvc.perform(get("/api/weather/23552"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'id': a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11, 'temperature': 10},{'id':'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12','temperature':20}]"));
    }

    @Test
    public void triggerOpenApiRequest() throws Exception {

        mockMvc.perform(post("/api/refresh"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("success!"));
    }


}
