package westmeijer.oskar.weatherapi.chart.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import westmeijer.oskar.weatherapi.chart.service.model.ChartLocation;
import westmeijer.oskar.weatherapi.openapi.server.chart.model.ChartLocationDto;
import westmeijer.oskar.weatherapi.openapi.server.chart.model.ChartLocationsResponse;

@Mapper(componentModel = "spring")
public interface ChartLocationsDtoMapper {

  default ChartLocationsResponse mapToResponse(List<ChartLocation> chartLocationList) {
    return new ChartLocationsResponse(mapList(chartLocationList));
  }

  List<ChartLocationDto> mapList(List<ChartLocation> chartLocationList);

  ChartLocationDto map(ChartLocation chartLocation);

}
