package westmeijer.oskar.weatherapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

public class TestLocationFactory {

  public static Location location() {
    Location location = new Location(1,
        UUID.randomUUID(),
        "1234",
        "5678",
        "Luebeck",
        "Germany",
        "GER",
        "51.659088",
        "6.966170",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Collections.emptyList());
    return location;
  }

  public static LocationEntity locationEntity() {
    LocationEntity location = new LocationEntity(1,
        UUID.randomUUID(),
        "1234",
        "5678",
        "Luebeck",
        "Germany",
        "GER",
        "51.659088",
        "6.966170",
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Instant.now().truncatedTo(ChronoUnit.MICROS),
        Collections.emptyList());
    return location;
  }

}
