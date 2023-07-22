package westmeijer.oskar.weatherapi.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import westmeijer.oskar.weatherapi.repository.model.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

  Location findFirstByOrderByLastImportAtAsc();

}
