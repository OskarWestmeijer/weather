package westmeijer.oskar.weatherapi.location.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public interface LocationRepository {

  List<Location> getAllOmitWeather();

  Location getByIdOmitWeather(Integer locationId);

  Location getNextImportLocation();

  List<Location> getAllWithLatest();

  Location save(Location location);
}
