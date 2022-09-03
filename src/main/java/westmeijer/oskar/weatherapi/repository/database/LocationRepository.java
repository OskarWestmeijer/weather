package westmeijer.oskar.weatherapi.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import westmeijer.oskar.weatherapi.entity.Location;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
