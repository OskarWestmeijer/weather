package westmeijer.oskar.weatherapi.overview.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.openapi.server.model.OverviewDto;
import westmeijer.oskar.weatherapi.openapi.server.model.OverviewResponse;
import westmeijer.oskar.weatherapi.overview.service.model.Overview;

@Mapper(componentModel = "spring")
public interface OverviewDtoMapper {

  default OverviewResponse mapToResponse(List<Overview> overviewList) {
    List<OverviewDto> overviewDtos = mapList(overviewList);
    OverviewResponse overviewResponse = new OverviewResponse();
    overviewResponse.setOverview(overviewDtos);
    return overviewResponse;
  }

  List<OverviewDto> mapList(List<Overview> overviewList);

  OverviewDto map(Overview overview);

}
