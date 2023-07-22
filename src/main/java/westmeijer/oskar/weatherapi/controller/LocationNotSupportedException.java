package westmeijer.oskar.weatherapi.controller;

/**
 * Is thrown when weather is requested for a not supported ZipCode.
 */
public class LocationNotSupportedException extends RuntimeException {

    private final String localZipCode;

    public LocationNotSupportedException(String localZipCode) {
        super(String.format("Location lookup for localZipCode  failed. localZipCode: %s)", localZipCode));
        this.localZipCode = localZipCode;
    }

    public String getZipCode() {
        return localZipCode;
    }
}
