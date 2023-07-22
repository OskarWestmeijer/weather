package westmeijer.oskar.weatherapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.service.WeatherApiService;
import westmeijer.oskar.weatherapi.repository.model.Weather;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherApiController.class)
public class WeatherApiControllerLayerTest {

  @MockBean
  private WeatherApiService weatherApiService;

  @MockBean
  private LocationJpaRepository locationJpaRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void requestWeatherKnownZipCode() throws Exception {

    List<Weather> weatherData = List.of(
        new Weather(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), 5.45, 88, 11.66, "23552", Instant.now(), Instant.now()));
    Optional<LocationEntity> location = Optional.of(new LocationEntity("23552", "2875601", "Lübeck", "Germany", Instant.now(), Instant.now()));
    when(weatherApiService.getLast24h("23552")).thenReturn(weatherData);
    when(locationJpaRepository.findById("23552")).thenReturn(location);

    mockMvc.perform(get("/api/v1/weather/23552/24h"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("""
            {
                "timeFormat" : "UTC",
                "cityName" : "Lübeck",
                "localZipCode" : "23552",
                "country" : "Germany",
                "weatherData" : [{"temperature": 5.45}]
            }"""));
  }

  @Test
  public void requestWeatherUnknownZipCode() throws Exception {
    mockMvc.perform(get("/api/v1/weather/46286/24h"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Requested zip_code not found. Please verify it is supported. zip_code: 46286"));
  }


}
