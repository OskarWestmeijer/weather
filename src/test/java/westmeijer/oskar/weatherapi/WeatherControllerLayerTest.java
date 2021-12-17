package westmeijer.oskar.weatherapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void requestWeatherKnownZipCode() throws Exception {
        mockMvc.perform(get("/api/weather/23552"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("The sun is shining in Lübeck!"));
    }

    @Test
    public void requestWeatherUnknownZipCode() throws Exception {
        mockMvc.perform(get("/api/weather/46286"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown zip code!"));
    }


}
