package westmeijer.oskar.weatherapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1")
public class WeatherExportController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherExportController.class);

    private final WeatherExportService weatherExportService;

    private final LocationRepository locationRepository;

    public WeatherExportController(WeatherExportService weatherExportService, LocationRepository locationRepository) {
        this.weatherExportService = weatherExportService;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/csv/weather/{localZipCode}/{date}")
    public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable int zipCode, @PathVariable String date) throws IOException {
        String fileName = ControllerUtil.buildFileName(zipCode, date, ControllerUtil.CSV_FILE);
        Location location = locationRepository.findById(zipCode).orElseThrow();

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), zipCode, ControllerUtil.atStartOfDay(date, location));
    }


}
