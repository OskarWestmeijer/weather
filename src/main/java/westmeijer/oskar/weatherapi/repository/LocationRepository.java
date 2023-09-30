package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.service.model.Location;

public interface LocationRepository {

  void updateLastImportAt(Location location);

  List<Location> getAll();

  Location getNextImportLocation();

  Location findByLocalZipCode(String localZipCode);
}
