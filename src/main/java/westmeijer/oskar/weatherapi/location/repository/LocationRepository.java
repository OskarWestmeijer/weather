package westmeijer.oskar.weatherapi.location.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.importjob.service.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public interface LocationRepository {

  List<Location> getAllOmitWeather();

  Location getById(Integer locationId);

  void updateLastImportAt(Integer locationId);

  Location getNextImportLocation();

}
