package westmeijer.oskar.weatherapi.export.controller;

import static java.util.Objects.requireNonNull;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import westmeijer.oskar.weatherapi.export.service.WeatherExportService;
import westmeijer.oskar.weatherapi.location.service.LocationService;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Controller
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class WeatherExportController {

  private static final String XML_FILE = ".xml";

  private static final String CSV_FILE = ".csv";

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private final WeatherExportService weatherExportService;

  private final LocationService locationService;

  @GetMapping("/csv/weather/{localZipCode}/{date}")
  public void exportAsCsv(HttpServletResponse servletResponse, @PathVariable String localZipCode, @PathVariable String date)
      throws IOException {
    requireNonNull(localZipCode, "localZipCode cannot be null");
    requireNonNull(date, "date cannot be null");
    log.info("Received weather export request. localZipCode: {}, date: {}", localZipCode, date);

    Location location = locationService.findByLocalZipCode(localZipCode);
    String fileName = buildFileName(location, date, CSV_FILE);

    servletResponse.setContentType("text/csv");
    servletResponse.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    weatherExportService.writeWeatherToCsv(servletResponse.getWriter(), localZipCode, atStartOfDay(date, location));
  }

  private static String buildFileName(Location location, String date, String type) {
    return "weather_" + date + "_" + location.countryCode() + "_" + location.cityName() + "_" + type;
  }

  private static Instant atStartOfDay(String date, Location location) {
    LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(date));
    ZoneId timezone = getTimeZone(location);
    return localDate.atStartOfDay(timezone).toInstant();
  }

  private static ZoneId getTimeZone(Location location) {
    return switch (location.countryCode()) {
      case "GER" -> ZoneId.of("Europe/Berlin");
      case "FIN" -> ZoneId.of("Europe/Helsinki");
      default -> throw new IllegalArgumentException("Export request for unknown location");
    };
  }

}
