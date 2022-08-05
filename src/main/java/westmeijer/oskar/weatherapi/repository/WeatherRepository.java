package westmeijer.oskar.weatherapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import westmeijer.oskar.weatherapi.model.Weather;

import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends CrudRepository<Weather, UUID> {

    @Query(value = "SELECT * FROM temperature ORDER BY timestamp DESC LIMIT 100", nativeQuery = true)
    List<Weather> getLatestEntries();

}
