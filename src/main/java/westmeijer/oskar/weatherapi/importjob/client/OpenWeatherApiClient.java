package westmeijer.oskar.weatherapi.importjob.client;

import static java.util.Objects.requireNonNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import westmeijer.oskar.weatherapi.importjob.client.mapper.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.importjob.exception.OpenWeatherApiRequestException;
import westmeijer.oskar.weatherapi.location.service.model.Location;
import westmeijer.oskar.weatherapi.openapi.client.api.GeneratedOpenWeatherApi;
import westmeijer.oskar.weatherapi.openapi.client.model.GeneratedOpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@Component
@Slf4j
public class OpenWeatherApiClient {

  private final OpenWeatherApiMapper openWeatherApiMapper;

  private final String appId;

  private final GeneratedOpenWeatherApi generatedOpenWeatherApi;

  public OpenWeatherApiClient(OpenWeatherApiMapper openWeatherApiMapper,
      GeneratedOpenWeatherApi generatedOpenWeatherApi,
      @Value("${openweatherapi.appId}") String appId) {
    requireNonNull(appId, "appId is required");
    this.openWeatherApiMapper = openWeatherApiMapper;
    this.appId = appId;
    this.generatedOpenWeatherApi = generatedOpenWeatherApi;
  }

  public Weather requestWithGeneratedClient(Location location) {
    try {
      requireNonNull(location, "location is required");

      ResponseEntity<GeneratedOpenWeatherApiResponse> response = generatedOpenWeatherApi.getCurrentWeatherWithHttpInfo(
          location.getLatitude(), location.getLongitude(), "metric", appId).block();

      requireNonNull(response, "response is required");
      GeneratedOpenWeatherApiResponse body = requireNonNull(response.getBody(), "body is required");

      return openWeatherApiMapper.mapToWeather(body, location);
    } catch (Exception e) {
      throw new OpenWeatherApiRequestException("Exception during OpenWeatherApi request.", e);
    }
  }

}
