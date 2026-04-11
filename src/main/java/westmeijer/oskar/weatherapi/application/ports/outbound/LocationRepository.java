package westmeijer.oskar.weatherapi.application.ports.outbound;

import java.util.List;
import westmeijer.oskar.weatherapi.domain.model.Location;

public interface LocationRepository {

  List<Location> getAllOmitWeather();

  Location getByIdOmitWeather(Integer locationId);

  Location getNextImportLocation();

  List<Location> getAllWithLatest();

  Location save(Location location);
}
