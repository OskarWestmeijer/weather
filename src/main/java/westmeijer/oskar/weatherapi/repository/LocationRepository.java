package westmeijer.oskar.weatherapi.repository;

import java.util.List;
import westmeijer.oskar.weatherapi.importjob.model.ImportJobLocation;
import westmeijer.oskar.weatherapi.service.model.Location;

public interface LocationRepository {

  void updateLastImportAt(ImportJobLocation location);

  List<Location> getAll();

  ImportJobLocation getNextImportLocation();

  Location findByLocalZipCode(String localZipCode);
}
