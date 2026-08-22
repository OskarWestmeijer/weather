package westmeijer.oskar.weatherapi.domain.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import java.time.Instant;
import java.util.Objects;
import java.util.regex.Pattern;

public record Overview(
    Integer locationId,
    String cityName,
    String countryCode,
    String latitude,
    String longitude,
    Double temperature,
    Integer humidity,
    Double windSpeed,
    Instant recordedAt) {

  private static final Pattern countryCodePattern = Pattern.compile("^[A-Z]{3}$");

  public Overview {
    Objects.requireNonNull(locationId, "locationId is required");
    checkArgument(!Strings.isNullOrEmpty(cityName), "cityName is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(countryCode) && countryCodePattern.matcher(countryCode).matches(),
        "countryCode is required (ISO 3166-1 alpha-3 code)", locationId);
    // TODO: latitude and longitude can be better validated / value-objects
    checkArgument(!Strings.isNullOrEmpty(latitude), "latitude is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(longitude), "longitude is required", locationId);
    Objects.requireNonNull(temperature, "temperature is required");
    Objects.requireNonNull(humidity, "humidity is required");
    Objects.requireNonNull(windSpeed, "windSpeed is required");
    Objects.requireNonNull(recordedAt, "recordedAt is required");
  }

}
