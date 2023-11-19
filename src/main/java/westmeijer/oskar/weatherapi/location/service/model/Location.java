package westmeijer.oskar.weatherapi.location.service.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {

  Integer locationId;
  UUID uuid;
  String localZipCode;
  String openWeatherApiLocationCode;
  String cityName;
  String country;
  String countryCode;
  String latitude;
  String longitude;
  Instant lastImportAt;
  List<Weather> weather = new ArrayList<>();

  public void addWeather(Weather weather) {
    this.weather.add(weather);
    weather.setLocation(this);
  }
}
