package westmeijer.oskar.weatherapi.weather.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.WebMvcMappersTestConfig;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

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
    Location location = TestLocationFactory.location();
    List<Weather> weatherList = List.of(
        new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, 88, 11.66, location,
            Instant.now().truncatedTo(
                ChronoUnit.MICROS)));

    given(locationService.getById(1)).willReturn(location);
    given(weatherService.getLast24h(1)).willReturn(weatherList);

    @Language("json")
    String expectedBody = """
        {
          "locationId" : 1,
          "cityName" : "Luebeck",
          "country" : "Germany",
          "weatherData" : [
            {
              "temperature": 5.45,
              "humidity": 88,
              "windSpeed":11.66
            }
          ]
        }""";

    mockMvc.perform(get("/weather/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedBody));

    then(weatherService).should().getLast24h(1);
    then(locationService).should().getById(1);
  }

  @Test
  @SneakyThrows
  public void expect404OnLocationNotFound() {
    given(locationService.getById(1)).willThrow(new LocationNotSupportedException(1));

    mockMvc.perform(get("/weather/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Requested locationId not found. Please verify it is supported. locationId: 1"));

    then(locationService).should().getById(1);
    then(weatherService).shouldHaveNoInteractions();
  }


}
