package westmeijer.oskar.weatherapi.application.ports.inbound;

import westmeijer.oskar.weatherapi.domain.model.Location;

public interface SaveLocationUseCase {

    Location save(Location location);

}
