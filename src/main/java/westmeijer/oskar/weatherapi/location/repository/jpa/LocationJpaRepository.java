package westmeijer.oskar.weatherapi.location.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

  @Query(value = """
      UPDATE weather.location SET last_import_at = now()
      WHERE id = :id""", nativeQuery = true)
  @Modifying
  void updateLastImportAt(@Param("id") Integer id);

  @Query(value = """
      SELECT * FROM weather.location
      WHERE id = :location_id AND id IS NOT null
      LIMIT 1""", nativeQuery = true)
  Optional<LocationEntity> getById(@Param("location_id") Integer locationId);

  @Query(value = """
      SELECT * FROM weather.location
      ORDER BY last_import_at ASC LIMIT 1""", nativeQuery = true)
  LocationEntity getNextImportLocation();

}
