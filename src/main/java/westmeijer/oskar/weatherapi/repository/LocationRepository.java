package westmeijer.oskar.weatherapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import westmeijer.oskar.weatherapi.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String> {
}
