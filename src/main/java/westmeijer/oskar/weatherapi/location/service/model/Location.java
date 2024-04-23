package westmeijer.oskar.weatherapi.location.service.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

public record Location(

    Integer locationId,
    UUID uuid,
    String localZipCode,
    String openWeatherApiLocationCode,
    String cityName,
    String country,
    String countryCode,
    String latitude,
    String longitude,
    Instant lastImportAt,
    List<Weather> weather) {

  public Location {
    Objects.requireNonNull(locationId, "locationId is required");
    Objects.requireNonNull(uuid, "uuid is required");
    checkArgument(!Strings.isNullOrEmpty(localZipCode), "localZipCode is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(openWeatherApiLocationCode), "openWeatherApiLocationCode is required", openWeatherApiLocationCode);
    checkArgument(!Strings.isNullOrEmpty(cityName), "cityName is required", cityName);
    checkArgument(!Strings.isNullOrEmpty(country), "country is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(countryCode), "countryCode is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(latitude), "latitude is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(longitude), "longitude is required", locationId);
    // TODO: lastImportAt is nullable. apply default values in db.
    Objects.requireNonNull(weather, "weather is required");
  }

}
