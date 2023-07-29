package westmeijer.oskar.weatherapi.openweatherapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;
import westmeijer.oskar.client.api.GeneratedOpenWeatherApiClient;
import westmeijer.oskar.client.api.OpenWeatherApi;
import westmeijer.oskar.weatherapi.openweatherapi.model.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.openweatherapi.model.OpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherApiClientTest {

  private WebClient webClient;
  private OpenWeatherApiMapper openWeatherApiMapper;

  private OpenWeatherApiClient openWeatherApiClient;

  @BeforeEach
  public void init() {
    String appId = "1234random";
    String urlPathTemplate = "/data/2.5/weather?id={location_code}&units=metric&appid={app_id}";
    this.webClient = mock(WebClient.class);
    this.openWeatherApiMapper = mock(OpenWeatherApiMapper.class);
    this.openWeatherApiClient = new OpenWeatherApiClient(webClient, openWeatherApiMapper, appId, urlPathTemplate, mock(
        GeneratedOpenWeatherApiClient.class));
  }

  @Test
  public void shouldRequestWeather() {
    Location requestLocation = new Location("00100", "658225", "Helsinki", "Finland", Instant.now().truncatedTo(ChronoUnit.MICROS));
    String expectedRequestUri = "/data/2.5/weather?id=658225&units=metric&appid=1234random";

    RequestHeadersUriSpec uriSpec = mock(RequestHeadersUriSpec.class);
    RequestHeadersUriSpec uriSpecWithPath = mock(RequestHeadersUriSpec.class);
    ResponseSpec responseSpec = mock(ResponseSpec.class);
    Mono apiResponseMono = mock(Mono.class);
    OpenWeatherApiResponse apiResponse = mock(OpenWeatherApiResponse.class);
    Weather expectedWeather = mock(Weather.class);

    given(webClient.get()).willReturn(uriSpec);
    given(uriSpec.uri(expectedRequestUri)).willReturn(uriSpecWithPath);
    given(uriSpecWithPath.retrieve()).willReturn(responseSpec);
    given(responseSpec.bodyToMono(OpenWeatherApiResponse.class)).willReturn(apiResponseMono);
    given(apiResponseMono.block()).willReturn(apiResponse);
    given(openWeatherApiMapper.map(apiResponse, requestLocation.localZipCode())).willReturn(expectedWeather);

    Weather actualWeather = openWeatherApiClient.requestWeather(requestLocation);

    assertThat(actualWeather).isEqualTo(expectedWeather);
    then(webClient).should().get();
    then(openWeatherApiMapper).should().map(apiResponse, requestLocation.localZipCode());
  }

  @DisplayName("Ensure error request handling is covered.")
  @Test
  public void handleErrorResponses() {
    Location requestLocation = new Location("66666", "666666", "Error", "Error", Instant.now());
    given(webClient.get()).willThrow(RuntimeException.class);

    assertThatThrownBy(() -> openWeatherApiClient.requestWeather(requestLocation))
        .isInstanceOf(OpenWeatherApiRequestException.class)
        .hasMessageContaining("Exception during OpenWeatherApi request.");
  }


}
