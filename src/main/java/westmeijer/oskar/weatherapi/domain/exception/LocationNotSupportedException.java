package westmeijer.oskar.weatherapi.domain.exception;

import lombok.Getter;

@Getter
public class LocationNotSupportedException extends RuntimeException {

    private final Integer locationId;

    public LocationNotSupportedException(Integer locationId) {
        super(String.format("Location lookup for locationId  failed. locationId: %s)", locationId));
        this.locationId = locationId;
    }

}
