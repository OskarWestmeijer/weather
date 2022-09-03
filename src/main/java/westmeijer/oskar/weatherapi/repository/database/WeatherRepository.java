package westmeijer.oskar.weatherapi.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(value = "SELECT * FROM weather WHERE timestamp BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW() AND zip_code = :zip_code ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getLatestEntries(@Param("zip_code") int zipCode);

    @Query(value = "SELECT * FROM weather WHERE timestamp BETWEEN NOW() - INTERVAL '3 DAYS' AND NOW() AND zip_code = :zip_code ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getLastThreeDays(@Param("zip_code") int zipCode);

    @Query(value = "SELECT * FROM weather WHERE timestamp >= :start_date AND timestamp <= :end_date AND zip_code = :zip_code ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getSpecificDay(@Param("zip_code") int zipCode, @Param("start_date") Instant start, @Param("end_date") Instant end);

}
