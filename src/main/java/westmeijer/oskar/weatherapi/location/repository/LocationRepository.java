package westmeijer.oskar.weatherapi.location.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public interface LocationRepository {

  void updateLastImportAt(Integer locationId);

  List<Location> getAll();

  Location getNextImportLocation();

  Location getById(Integer locationId);
}
