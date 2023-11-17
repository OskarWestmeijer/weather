package westmeijer.oskar.weatherapi.importjob.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ImportJobLocation {

  private final Integer locationId;
  private final String latitude;
  private final String longitude;

  @Setter
  private Weather weather;

}
