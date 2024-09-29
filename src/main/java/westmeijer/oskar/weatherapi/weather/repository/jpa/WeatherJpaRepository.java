package westmeijer.oskar.weatherapi.weather.repository.jpa;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;

public interface WeatherJpaRepository extends JpaRepository<WeatherEntity, UUID> {

  @Query(value = """
      SELECT * FROM weather.weather
      WHERE location_id = :locationId
      AND recorded_at >= :from
      ORDER BY recorded_at ASC
      LIMIT :limit
      """, nativeQuery = true)
  List<WeatherEntity> getWeather(Integer locationId, Instant from, Integer limit);

  @Query(value = """
      SELECT COUNT(*)
      FROM weather.weather
      WHERE location_id = :locationId
      AND recorded_at >= :from
      """, nativeQuery = true)
  int getTotalCount(Integer locationId, Instant from);
}
