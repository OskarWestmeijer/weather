package westmeijer.oskar.weatherapi.dal.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.entity.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(value = "SELECT * FROM weather WHERE timestamp BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW() ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getLatestEntries();

    @Query(value = "SELECT * FROM weather WHERE timestamp BETWEEN NOW() - INTERVAL '3 DAYS' AND NOW() ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getLastThreeDays();

    @Query(value = "SELECT * FROM weather WHERE timestamp >= :start_date AND timestamp <= :end_date ORDER BY timestamp DESC", nativeQuery = true)
    List<Weather> getSpecificDay(@Param("start_date") Instant start, @Param("end_date") Instant end);

}
