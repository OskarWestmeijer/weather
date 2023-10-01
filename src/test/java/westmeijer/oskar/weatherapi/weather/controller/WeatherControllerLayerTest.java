package westmeijer.oskar.weatherapi.weather.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;
import westmeijer.oskar.weatherapi.WebMvcMappersTestConfig;
import westmeijer.oskar.weatherapi.weather.controller.WeatherController;

@WebMvcTest(WeatherController.class)
@Import(WebMvcMappersTestConfig.class)
public class WeatherControllerLayerTest {

  @MockBean
  private WeatherService weatherService;

  @MockBean
  private LocationService locationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  public void shouldRequestWeatherLast24h() {
    List<Weather> weatherList = List.of(
        new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, 88, 11.66, "23552", 1, Instant.now()));
    Location location = new Location(1, "23552", "2875601", "Lübeck", "Germany", Instant.now());

    given(weatherService.getLast24h("23552")).willReturn(weatherList);
    given(locationService.findByLocalZipCode("23552")).willReturn(location);

    @Language("json")
    String expectedBody = """
        {
          "cityName" : "Lübeck",
          "localZipCode" : "23552",
          "country" : "Germany",
          "weatherData" : [
            {
              "temperature": 5.45,
              "humidity": 88
            }
          ]
        }""";

    mockMvc.perform(get("/weather/23552/24h"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedBody));

    then(weatherService).should().getLast24h("23552");
    then(locationService).should().findByLocalZipCode("23552");
  }

  @Test
  @SneakyThrows
  public void expect404OnLocationNotFound() {
    given(locationService.findByLocalZipCode("46286")).willThrow(new LocationNotSupportedException("46286"));

    mockMvc.perform(get("/weather/46286/24h"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Requested zip_code not found. Please verify it is supported. zip_code: 46286"));

    then(weatherService).shouldHaveNoInteractions();
    then(locationService).should().findByLocalZipCode("46286");
  }


}
