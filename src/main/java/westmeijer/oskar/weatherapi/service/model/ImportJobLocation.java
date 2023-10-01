package westmeijer.oskar.weatherapi.service.model;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

public record ImportJobLocation(@Nonnull Integer id,
                                @Nonnull String latitude,
                                @Nonnull String longitude,
                                @Nonnull String localZipCode) {

  public ImportJobLocation {
    requireNonNull(id, "id cannot be null");
    requireNonNull(latitude, "latitude cannot be null");
    requireNonNull(longitude, "longitude cannot be null");
    requireNonNull(localZipCode, "localZipCode cannot be null");
  }

}
