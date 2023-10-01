package westmeijer.oskar.weatherapi.importjob.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import westmeijer.oskar.openapi.client.api.GeneratedOpenWeatherApi;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.importjob.exception.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.importjob.client.mapper.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherApiClientTest {

  private OpenWeatherApiMapper openWeatherApiMapper = mock(OpenWeatherApiMapper.class);

  private final GeneratedOpenWeatherApi generatedOpenWeatherApi = mock(GeneratedOpenWeatherApi.class);

  private final String appId = "1234random";

  private OpenWeatherApiClient openWeatherApiClient;

  @BeforeEach
  public void init() {
    openWeatherApiClient = new OpenWeatherApiClient(openWeatherApiMapper, generatedOpenWeatherApi, appId);
  }

  @Test
  public void shouldRequestWeather() {
    ImportJobLocation requestLocation = mock(ImportJobLocation.class);

    Mono<ResponseEntity<GeneratedOpenWeatherApiResponse>> apiResponseMono = mock(Mono.class);
    ResponseEntity<GeneratedOpenWeatherApiResponse> apiResponseEntity = mock(ResponseEntity.class);
    GeneratedOpenWeatherApiResponse apiResponse = mock(GeneratedOpenWeatherApiResponse.class);
    Weather expectedWeather = mock(Weather.class);

    given(generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(requestLocation.latitude(),
        requestLocation.longitude(), "metric", appId)).willReturn(apiResponseMono);
    given(apiResponseMono.block()).willReturn(apiResponseEntity);
    given(apiResponseEntity.getBody()).willReturn(apiResponse);
    given(openWeatherApiMapper.map(apiResponse, requestLocation.localZipCode(), requestLocation.id())).willReturn(expectedWeather);

    Weather actualWeather = openWeatherApiClient.requestWithGeneratedClient(requestLocation);

    assertThat(actualWeather).isEqualTo(expectedWeather);
  }

  @Test
  public void handleErrorResponses() {
    ImportJobLocation requestLocation = mock(ImportJobLocation.class);
    given(generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(requestLocation.latitude(), requestLocation.longitude(), "metric",
        appId)).willThrow(
        RuntimeException.class);

    assertThatThrownBy(() -> openWeatherApiClient.requestWithGeneratedClient(requestLocation))
        .isInstanceOf(OpenWeatherApiRequestException.class)
        .hasMessageContaining("Exception during OpenWeatherApi request.");
  }


}
