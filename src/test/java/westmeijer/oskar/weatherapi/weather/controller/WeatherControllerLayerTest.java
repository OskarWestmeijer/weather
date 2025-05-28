package westmeijer.oskar.weatherapi.weather.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import lombok.SneakyThrows;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import westmeijer.oskar.weatherapi.TestLocationFactory;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.WebMvcMappersTestConfig;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.weather.service.WeatherService;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage.PagingDetails;

@WebMvcTest(WeatherController.class)
@Import(WebMvcMappersTestConfig.class)
public class WeatherControllerLayerTest {

  @MockitoBean
  private WeatherService weatherService;

  @MockitoBean
  private LocationService locationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @SneakyThrows
  void shouldGetWeatherForRequestParams() {
    Location location = TestLocationFactory.locationWithoutWeather();
    Weather weather = TestWeatherFactory.weather();
    List<Weather> weatherList = List.of(weather);

    var requestFrom = Instant.now();
    var requestLimit = 5;

    var pagingDetails = PagingDetails.builder()
        .totalRecords(weatherList.size())
        .nextFrom(Instant.now())
        .nextLink("nextLink")
        .pageRecords(weatherList.size())
        .hasNewerRecords(false)
        .build();

    var weatherFeedPage = WeatherFeedPage.builder()
        .weatherList(weatherList)
        .pagingDetails(pagingDetails)
        .build();

    given(locationService.getByIdOmitWeather(1)).willReturn(location);
    given(weatherService.getWeatherFeedPage(1, requestFrom, requestLimit)).willReturn(weatherFeedPage);

    @Language("json")
    String expectedBody = """
        {
          "locationId" : 1,
          "cityName" : "Luebeck",
          "country" : "Germany",
          "weatherData" : [
            {
              "temperature": 25.34,
              "humidity": 55,
              "windSpeed":10.34
            }
          ],
          "pagingDetails": {
            "pageRecordsCount": %s,
            "totalRecordsCount": %s,
            "hasNewerRecords": %s,
            "nextFrom": "%s",
            "nextLink": "%s"
          }
        }""";

    mockMvc.perform(get("/weather?locationId=1&from=%s&limit=%s".formatted(requestFrom, requestLimit)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            expectedBody.formatted(pagingDetails.pageRecords(), pagingDetails.totalRecords(), pagingDetails.hasNewerRecords(),
                pagingDetails.nextFrom(), pagingDetails.nextLink())));

    then(locationService).should().getByIdOmitWeather(1);
    then(weatherService).should().getWeatherFeedPage(1, requestFrom, requestLimit);
  }

  @Test
  @SneakyThrows
  public void expect404OnLocationNotFound() {
    given(locationService.getByIdOmitWeather(1)).willThrow(new LocationNotSupportedException(1));

    mockMvc.perform(get("/weather?locationId=1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Requested locationId not found. Please verify it is supported. locationId: 1"));

    then(locationService).should().getByIdOmitWeather(1);
    then(weatherService).shouldHaveNoInteractions();
  }

  @Test
  @SneakyThrows
  void locationIdIsRequired() {
    mockMvc.perform(get("/weather"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(
            "{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Required parameter 'locationId' is not present.\",\"instance\":\"/weather\"}"));

    then(locationService).shouldHaveNoInteractions();
    then(weatherService).shouldHaveNoInteractions();
  }

  @Test
  @SneakyThrows
  void shouldUseDefaultsForFromAndLimit() {
    Location location = TestLocationFactory.locationWithoutWeather();
    Weather weather = TestWeatherFactory.weather();
    List<Weather> weatherList = List.of(weather);

    var pagingDetails = PagingDetails.builder()
        .totalRecords(weatherList.size())
        .nextFrom(Instant.now())
        .nextLink("nextLink")
        .pageRecords(weatherList.size())
        .hasNewerRecords(false)
        .build();

    var weatherFeedPage = WeatherFeedPage.builder()
        .weatherList(weatherList)
        .pagingDetails(pagingDetails)
        .build();

    Integer defaultLimit = 1000;
    Instant defaultFrom = Instant.EPOCH;

    given(locationService.getByIdOmitWeather(1)).willReturn(location);
    given(weatherService.getWeatherFeedPage(location.locationId(), defaultFrom, defaultLimit)).willReturn(weatherFeedPage);

    @Language("json")
    String expectedBody = """
        {
          "locationId" : 1,
          "cityName" : "Luebeck",
          "country" : "Germany",
          "weatherData" : [
            {
              "temperature": 25.34,
              "humidity": 55,
              "windSpeed":10.34
            }
          ],
          "pagingDetails": {
            "pageRecordsCount": %s,
            "totalRecordsCount": %s,
            "hasNewerRecords": %s,
            "nextFrom": "%s",
            "nextLink": "%s"
          }
        }""";

    mockMvc.perform(get("/weather?locationId=1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            expectedBody.formatted(pagingDetails.pageRecords(), pagingDetails.totalRecords(), pagingDetails.hasNewerRecords(),
                pagingDetails.nextFrom(), pagingDetails.nextLink())));

    then(locationService).should().getByIdOmitWeather(1);
    then(weatherService).should().getWeatherFeedPage(location.locationId(), defaultFrom, defaultLimit);
  }


}
