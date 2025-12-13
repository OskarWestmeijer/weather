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
import westmeijer.oskar.weatherapi.importjob.client.mapper.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.importjob.exception.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.api.GeneratedOpenWeatherApi;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherApiClientTest {

  private final OpenWeatherApiMapper openWeatherApiMapper = mock(OpenWeatherApiMapper.class);

  private final GeneratedOpenWeatherApi generatedOpenWeatherApi = mock(GeneratedOpenWeatherApi.class);

  private final String appId = "1234random";

  private OpenWeatherApiClient openWeatherApiClient;

  @BeforeEach
  public void init() {
    openWeatherApiClient = new OpenWeatherApiClient(openWeatherApiMapper, generatedOpenWeatherApi, appId);
  }

  @Test
  public void shouldRequestWeather() {
    Location requestLocation = mock(Location.class);

    ResponseEntity<GeneratedOpenWeatherApiResponse> apiResponseEntity = mock(ResponseEntity.class);
    GeneratedOpenWeatherApiResponse apiResponse = mock(GeneratedOpenWeatherApiResponse.class);
    Location expectedLocation = mock(Location.class);

    given(generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(requestLocation.latitude(),
        requestLocation.longitude(), "metric", appId)).willReturn(apiResponseEntity);
    given(apiResponseEntity.getBody()).willReturn(apiResponse);
    given(openWeatherApiMapper.mapToLocation(apiResponse, requestLocation)).willReturn(expectedLocation);

    Location actualLocation = openWeatherApiClient.requestWithGeneratedClient(requestLocation);

    assertThat(actualLocation).isEqualTo(expectedLocation);
  }

  @Test
  public void handleErrorResponses() {
    Location requestLocation = mock(Location.class);
    given(generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(requestLocation.latitude(), requestLocation.longitude(), "metric",
        appId)).willThrow(
        RuntimeException.class);

    assertThatThrownBy(() -> openWeatherApiClient.requestWithGeneratedClient(requestLocation))
        .isInstanceOf(OpenWeatherApiRequestException.class)
        .hasMessageContaining("Exception during OpenWeatherApi request.");
  }


}
