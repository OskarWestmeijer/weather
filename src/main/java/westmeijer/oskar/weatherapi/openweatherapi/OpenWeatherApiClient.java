package westmeijer.oskar.weatherapi.openweatherapi;

import static java.util.Objects.requireNonNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import westmeijer.oskar.openapi.client.api.GeneratedOpenWeatherApi;
import westmeijer.oskar.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.openweatherapi.mapper.OpenWeatherApiMapper;
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
    requireNonNull(appId, "appId must not be null");
    this.openWeatherApiMapper = openWeatherApiMapper;
    this.appId = appId;
    this.generatedOpenWeatherApi = generatedOpenWeatherApi;
  }

  public Weather requestWithGeneratedClient(Location location) {
    try {
      requireNonNull(location, "Location cannot be null.");
      ResponseEntity<GeneratedOpenWeatherApiResponse> response = generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(
          location.openWeatherApiLocationCode(), "metric", appId).block();
      return openWeatherApiMapper.map(requireNonNull(response.getBody(), "ResponseBody cannot be null"), location.localZipCode());
    } catch (Exception e) {
      throw new OpenWeatherApiRequestException("Exception during OpenWeatherApi request.", e);
    }
  }

}
