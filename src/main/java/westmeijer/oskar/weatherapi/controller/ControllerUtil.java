package westmeijer.oskar.weatherapi.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import westmeijer.oskar.weatherapi.service.model.Location;

public class ControllerUtil {

  public static final String XML_FILE = ".xml";
  public static final String CSV_FILE = ".csv";

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static Instant atStartOfDay(String date, Location location) {
    LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(date));
    ZoneId timezone = getTimeZone(location);
    return localDate.atStartOfDay(timezone).toInstant();
  }

  public static String buildFileName(String localZipCode, String date, String type) {
    return "weather_" + date + "_" + localZipCode + type;
  }

  private static ZoneId getTimeZone(Location location) {

    return switch (location.country()) {
      case "Germany" -> ZoneId.of("Europe/Berlin");
      case "Finland" -> ZoneId.of("Europe/Helsinki");
      default -> ZoneId.of("UTC");
    };


  }


}
