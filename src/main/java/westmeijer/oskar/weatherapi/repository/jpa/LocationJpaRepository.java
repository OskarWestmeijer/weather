package westmeijer.oskar.weatherapi.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

  LocationEntity findFirstByOrderByLastImportAtAsc();

}
