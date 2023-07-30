package westmeijer.oskar.weatherapi.openweatherapi;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import westmeijer.oskar.openapi.client.api.GeneratedOpenWeatherApi;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.openweatherapi.model.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Component
@Slf4j
public class OpenWeatherApiClient {

  private final OpenWeatherApiMapper openWeatherApiMapper;

  private final String appId;

  private final GeneratedOpenWeatherApi generatedOpenWeatherApi;

  public OpenWeatherApiClient(OpenWeatherApiMapper openWeatherApiMapper,
      GeneratedOpenWeatherApi generatedOpenWeatherApi,
      @Value("${openweatherapi.appId}") String appId) {
    this.openWeatherApiMapper = openWeatherApiMapper;
    this.appId = appId;
    this.generatedOpenWeatherApi = generatedOpenWeatherApi;
  }

  public Weather requestWithGeneratedClient(Location location) {
    try {
      ResponseEntity<GeneratedOpenWeatherApiResponse> response = generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(
          location.locationCode(), "metric", appId).block();
      return openWeatherApiMapper.map(Objects.requireNonNull(response.getBody()), location.localZipCode());
    } catch (Exception e) {
      throw new OpenWeatherApiRequestException("Exception during OpenWeatherApi request.", e);
    }
  }

}
