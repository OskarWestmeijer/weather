package westmeijer.oskar.weatherapi.application.ports.inbound;

import java.util.List;
import westmeijer.oskar.weatherapi.domain.model.Overview;

public interface GetOverviewUseCase {

  List<Overview> getOverview();

}
