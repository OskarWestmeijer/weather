package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import westmeijer.oskar.weatherapi.openapi.server.api.OverviewApi;
import westmeijer.oskar.weatherapi.openapi.server.model.OverviewResponse;
import westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.mappers.OverviewDtoMapper;
import westmeijer.oskar.weatherapi.application.services.OverviewService;
import westmeijer.oskar.weatherapi.domain.model.Overview;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class OverviewController implements OverviewApi {

  private final OverviewDtoMapper overviewDtoMapper;

  private final OverviewService overviewService;

  @Override
  public ResponseEntity<OverviewResponse> getOverview() {
    List<Overview> overviews = overviewService.getOverview();
    OverviewResponse overviewResponse = overviewDtoMapper.mapToResponse(overviews);
    return ResponseEntity.ok(overviewResponse);
  }
}
