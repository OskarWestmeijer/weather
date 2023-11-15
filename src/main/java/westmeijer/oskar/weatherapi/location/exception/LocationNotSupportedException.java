package westmeijer.oskar.weatherapi.location.exception;

/**
 * Is thrown when weather is requested for a not supported locationId.
 */
public class LocationNotSupportedException extends RuntimeException {

  private final Integer locationId;

  public LocationNotSupportedException(Integer locationId) {
    super(String.format("Location lookup for locationId  failed. locationId: %s)", locationId));
    this.locationId = locationId;
  }

  public Integer getLocationId() {
    return locationId;
  }
}
