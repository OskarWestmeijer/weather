package westmeijer.oskar.weatherapi.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import westmeijer.oskar.weatherapi.repository.model.Weather;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {

    @Query(value = "SELECT * FROM weather.weather WHERE recorded_at BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW() AND local_zip_code = :local_zip_code ORDER BY recorded_at DESC", nativeQuery = true)
    List<Weather> getLatestEntries(@Param("local_zip_code") String localZipCode);

    @Query(value = "SELECT * FROM weather.weather WHERE recorded_at BETWEEN NOW() - INTERVAL '3 DAYS' AND NOW() AND local_zip_code = :local_zip_code ORDER BY recorded_at DESC", nativeQuery = true)
    List<Weather> getLastThreeDays(@Param("local_zip_code") String localZipCode);

    @Query(value = "SELECT * FROM weather.weather WHERE recorded_at >= :start_date AND recorded_at <= :end_date AND local_zip_code = :local_zip_code ORDER BY recorded_at DESC", nativeQuery = true)
    List<Weather> getSpecificDay(@Param("local_zip_code") String localZipCode, @Param("start_date") Instant start, @Param("end_date") Instant end);

}
