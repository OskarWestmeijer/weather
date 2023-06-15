package westmeijer.oskar.weatherapi.repository;

/**
 * Is thrown when weather is requested for a not supported ZipCode.
 */
public class LocationNotSupportedException extends RuntimeException {

    private final int zipCode;

    public LocationNotSupportedException(int zipCode) {
        super(String.format("Location lookup for localZipCode  failed. localZipCode: %s)", zipCode));
        this.zipCode = zipCode;
    }

    public int getZipCode() {
        return zipCode;
    }
}
