package westmeijer.oskar.weatherapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import westmeijer.oskar.weatherapi.model.WeatherEntity;

import java.util.List;
import java.util.UUID;

public interface WeatherRepository extends CrudRepository<WeatherEntity, UUID> {

    @Query(value = "SELECT * FROM temperature ORDER BY timestamp DESC LIMIT 100", nativeQuery = true)
    List<WeatherEntity> getLatestEntries();

}
