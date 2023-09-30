package westmeijer.oskar.weatherapi.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

  @Query(value = "SELECT * FROM weather.location WHERE local_zip_code = :local_zip_code LIMIT 1", nativeQuery = true)
  Optional<LocationEntity> findByLocalZipCode(@Param("local_zip_code") String localZipCode);

  LocationEntity findFirstByOrderByLastImportAtAsc();

}
