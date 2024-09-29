package westmeijer.oskar.weatherapi.location.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;

public interface LocationJpaRepository extends JpaRepository<LocationEntity, String> {

  @Query("""
      SELECT l FROM LocationEntity l ORDER BY l.id ASC
      """)
  List<LocationEntity> getAll();

  @Query(value = "SELECT l FROM LocationEntity l WHERE l.id = ?1")
  Optional<LocationEntity> getById(@Param("location_id") Integer locationId);

  @Query(value = """
      SELECT * FROM weather.location
      ORDER BY last_import_at ASC LIMIT 1""", nativeQuery = true)
  LocationEntity getNextImportLocation();

  @Query("""
      SELECT l FROM LocationEntity l
      JOIN FETCH l.weather w
      WHERE w.recordedAt = (SELECT MAX(w2.recordedAt) FROM WeatherEntity w2 WHERE w2.location = l)
      ORDER BY l.id ASC""")
  List<LocationEntity> getAllWithLatest();

}
