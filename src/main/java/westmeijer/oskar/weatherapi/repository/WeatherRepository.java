package westmeijer.oskar.weatherapi.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WeatherRepository extends CrudRepository<WeatherEntity, UUID> {
}
