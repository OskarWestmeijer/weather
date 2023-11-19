package westmeijer.oskar.weatherapi.weather.service.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Weather {

  UUID id;
  Double temperature;
  Integer humidity;
  Double windSpeed;
  Location location;
  Instant recordedAt;

}
