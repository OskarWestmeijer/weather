package westmeijer.oskar.weatherapi.location.service.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
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

  private static final Pattern countryCodePattern = Pattern.compile("^[A-Z]{3}$");

  public Location {
    Objects.requireNonNull(locationId, "locationId is required");
    Objects.requireNonNull(uuid, "uuid is required");
    checkArgument(StringUtils.isNumeric(localZipCode), "localZipCode is required and must be numeric", locationId);
    checkArgument(StringUtils.isNumeric(openWeatherApiLocationCode), "openWeatherApiLocationCode is required and must be numeric",
        locationId);
    checkArgument(!Strings.isNullOrEmpty(cityName), "cityName is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(country), "country is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(countryCode) && countryCodePattern.matcher(countryCode).matches(),
        "countryCode is required (ISO 3166-1 alpha-3 code)", locationId);
    // TODO: latitude and longitude can be better validated / value-objects
    checkArgument(!Strings.isNullOrEmpty(latitude), "latitude is required", locationId);
    checkArgument(!Strings.isNullOrEmpty(longitude), "longitude is required", locationId);
    // TODO: lastImportAt is nullable. this can be changed by applying default values in db.
    Objects.requireNonNull(weather, "weather is required");
  }

}
