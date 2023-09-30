package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.service.model.Location;

public interface LocationRepository {

  List<Location> getAll();

  Location getNextImportLocation();

  Location saveAndFlush(Location location);

  Location findByLocalZipCode(String localZipCode);
}
