package westmeijer.oskar.weatherapi.repository.database;

/**
 * Is thrown when weather is requested for a not supported ZipCode.
 */
public class LocationNotSupportedException extends RuntimeException {

    private final int zipCode;

    public LocationNotSupportedException(int zipCode) {
        super(String.format("Location lookup for zipCode  failed. zipCode: %s)", zipCode));
        this.zipCode = zipCode;
    }

    public int getZipCode() {
        return zipCode;
    }
}
