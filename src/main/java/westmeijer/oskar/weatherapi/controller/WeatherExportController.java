package westmeijer.oskar.weatherapi.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.service.LocationService;
import westmeijer.oskar.weatherapi.service.WeatherExportService;
import westmeijer.oskar.weatherapi.service.model.Location;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WeatherExportController {

  private final WeatherExportService weatherExportService;

  private final LocationService locationService;

  @GetMapping("/csv/weather/{localZipCode}/{date}")
  public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable String localZipCode, @PathVariable String date)
      throws IOException {
    String fileName = ControllerUtil.buildFileName(localZipCode, date, ControllerUtil.CSV_FILE);
    Location location = locationService.findById(localZipCode);

    servletResponse.setContentType("text/csv");
    servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), localZipCode, ControllerUtil.atStartOfDay(date, location));
  }


}
