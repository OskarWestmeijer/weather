package westmeijer.oskar.weatherapi.export.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.export.service.WeatherExportService;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.util.controller.ControllerUtil;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Controller
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class WeatherExportController {

  private final WeatherExportService weatherExportService;

  private final LocationService locationService;

  @GetMapping("/csv/weather/{localZipCode}/{date}")
  public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable String localZipCode, @PathVariable String date)
      throws IOException {
    String fileName = ControllerUtil.buildFileName(localZipCode, date, ControllerUtil.CSV_FILE);
    Location location = locationService.findByLocalZipCode(localZipCode);

    servletResponse.setContentType("text/csv");
    servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), localZipCode, ControllerUtil.atStartOfDay(date, location));
  }


}
