package westmeijer.oskar.weatherapi.weather.service.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Weather {

  UUID id;
  Double temperature;
  Integer humidity;
  Double windSpeed;
  Location location;
  Instant recordedAt;

}
