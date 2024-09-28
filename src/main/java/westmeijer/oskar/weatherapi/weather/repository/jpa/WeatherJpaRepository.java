package westmeijer.oskar.weatherapi.weather.repository.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;

public interface WeatherJpaRepository extends JpaRepository<WeatherEntity, UUID> {

  @Query(value = """
      SELECT * FROM weather.weather
      WHERE recorded_at BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW() AND location_id = :location_id
      ORDER BY recorded_at DESC""", nativeQuery = true)
  List<WeatherEntity> getLast24h(@Param("location_id") Integer locationId);

  @Query(value = """
      SELECT * FROM weather.weather
      WHERE location_id = :location_id
      ORDER BY recorded_at DESC LIMIT 1""", nativeQuery = true)
  WeatherEntity getLatest(@Param("location_id") Integer locationId);

}
