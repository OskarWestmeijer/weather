package westmeijer.oskar.weatherapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;

public class TestOverviewFactory {

  public static Overview overview() {

    return new Overview(1, "Luebeck", "GER", 15.23, 44, 9.2, Instant.now().truncatedTo(ChronoUnit.MICROS));
  }

}
