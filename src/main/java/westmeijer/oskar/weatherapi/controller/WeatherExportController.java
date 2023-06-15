package westmeijer.oskar.weatherapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherExportService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1")
public class WeatherExportController {

    private final WeatherExportService weatherExportService;

    private final LocationRepository locationRepository;

    public WeatherExportController(WeatherExportService weatherExportService, LocationRepository locationRepository) {
        this.weatherExportService = weatherExportService;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/csv/weather/{localZipCode}/{date}")
    public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable String localZipCode, @PathVariable String date) throws IOException {
        String fileName = ControllerUtil.buildFileName(localZipCode, date, ControllerUtil.CSV_FILE);
        Location location = locationRepository.findById(localZipCode).orElseThrow();

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), localZipCode, ControllerUtil.atStartOfDay(date, location));
    }


}
