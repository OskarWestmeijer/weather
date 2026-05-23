package westmeijer.oskar.weatherapi.application.ports.inbound;

import westmeijer.oskar.weatherapi.domain.model.Location;

import java.util.List;

public interface GetLocationUseCase {

    Location getByIdOmitWeather(Integer locationId);

    List<Location> getAllWithLatest();

    List<Location> getAllOmitWeather();

    Location getNextImportLocation();

}
