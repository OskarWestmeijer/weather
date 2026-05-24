package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import westmeijer.oskar.weatherapi.application.ports.inbound.GetOverviewUseCase;
import westmeijer.oskar.weatherapi.domain.model.Overview;
import westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.mappers.OverviewDtoMapper;
import westmeijer.oskar.weatherapi.openapi.server.api.OverviewApi;
import westmeijer.oskar.weatherapi.openapi.server.model.OverviewResponse;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class OverviewController implements OverviewApi {

  private final OverviewDtoMapper overviewDtoMapper;

  private final GetOverviewUseCase getOverviewUseCase;

  @Override
  public ResponseEntity<OverviewResponse> getOverview() {
    List<Overview> overviews = getOverviewUseCase.getOverview();
    OverviewResponse overviewResponse = overviewDtoMapper.mapToResponse(overviews);
    log.info("Returning overview response. locations count: {}", overviews.size());
    return ResponseEntity.ok(overviewResponse);
  }
}
