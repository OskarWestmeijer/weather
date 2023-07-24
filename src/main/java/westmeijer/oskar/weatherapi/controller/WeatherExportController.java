package westmeijer.oskar.weatherapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.service.WeatherExportService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1")
public class WeatherExportController {

  private final WeatherExportService weatherExportService;

  private final LocationJpaRepository locationJpaRepository;

  public WeatherExportController(WeatherExportService weatherExportService, LocationJpaRepository locationJpaRepository) {
    this.weatherExportService = weatherExportService;
    this.locationJpaRepository = locationJpaRepository;
  }

  @GetMapping("/csv/weather/{localZipCode}/{date}")
  public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable String localZipCode, @PathVariable String date)
      throws IOException {
    String fileName = ControllerUtil.buildFileName(localZipCode, date, ControllerUtil.CSV_FILE);
    LocationEntity locationEntity = locationJpaRepository.findById(localZipCode).orElseThrow();

    servletResponse.setContentType("text/csv");
    servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), localZipCode, ControllerUtil.atStartOfDay(date, locationEntity));
  }


}
