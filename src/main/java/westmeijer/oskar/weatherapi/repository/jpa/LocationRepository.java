package westmeijer.oskar.weatherapi.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {

  LocationEntity findFirstByOrderByLastImportAtAsc();

}
