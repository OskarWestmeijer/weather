package westmeijer.oskar.weatherapi.openweatherapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import westmeijer.oskar.weatherapi.openweatherapi.model.OpenWeatherApiMapper;
import westmeijer.oskar.weatherapi.openweatherapi.model.OpenWeatherApiResponse;
import westmeijer.oskar.weatherapi.service.model.Location;
import westmeijer.oskar.weatherapi.service.model.Weather;

@Component
@Slf4j
public class OpenWeatherApiClient {

  private final WebClient webClient;

  private final OpenWeatherApiMapper openWeatherApiMapper;

  private final String urlPathTemplate;
  private final String appId;

  public OpenWeatherApiClient(WebClient webClient, OpenWeatherApiMapper openWeatherApiMapper, @Value("${openweatherapi.appId}") String appId,
      @Value("${openweatherapi.urlPathTemplate}") String urlPathTemplate) {
    this.webClient = webClient;
    this.openWeatherApiMapper = openWeatherApiMapper;
    this.urlPathTemplate = urlPathTemplate;
    this.appId = appId;
  }

  public Weather requestWeather(Location location) {
    try {
      String urlPath = buildUrlPath(location);
      log.debug("Built urlPath: {}", urlPath);
      OpenWeatherApiResponse response = webClient.get().uri(urlPath).retrieve().bodyToMono(OpenWeatherApiResponse.class).block();
      log.debug("OpenWeatherApiResponse: {}", response);
      return openWeatherApiMapper.map(response, location.localZipCode());
    } catch (Exception e) {
      throw new OpenWeatherApiRequestException("Exception during OpenWeatherApi request.", e);
    }
  }

  private String buildUrlPath(Location location) {
    String path = urlPathTemplate.replace(RequestParamPlaceholders.LOCATION_CODE_CHAR_SEQUENCE.getValue(), String.valueOf(
        location.locationCode()));
    return path.replace(RequestParamPlaceholders.APP_ID_CHAR_SEQUENCE.getValue(), appId);
  }

}
