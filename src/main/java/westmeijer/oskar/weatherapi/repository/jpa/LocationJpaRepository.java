package westmeijer.oskar.weatherapi.repository.jpa;

import java.time.Instant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

  @Query(value = "UPDATE weather.location SET last_import_at = now() WHERE id = :id", nativeQuery = true)
  @Modifying
  int updateLastImportAt(@Param("id") Integer id);

  @Query(value = "SELECT * FROM weather.location WHERE local_zip_code = :local_zip_code AND id IS NOT null LIMIT 1", nativeQuery = true)
  Optional<LocationEntity> findByLocalZipCode(@Param("local_zip_code") String localZipCode);

  @Query(value = "SELECT * FROM weather.location WHERE id IS NOT NULL ORDER BY last_import_at ASC LIMIT 1", nativeQuery = true)
  LocationEntity findFirstByOrderByLastImportAtAsc();

}
