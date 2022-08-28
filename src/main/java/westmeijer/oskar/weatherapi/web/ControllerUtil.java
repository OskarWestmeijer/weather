package westmeijer.oskar.weatherapi.web;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ControllerUtil {

    public static final String XML_FILE = ".xml";
    public static final String CSV_FILE = ".csv";


    public static Instant parse(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.from(formatter.parse(date));
        return localDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
    }

    public static String buildFileName(int zipCode, String date, String type) {
        return new StringBuilder().append("weather_").append(date).append("_").append(zipCode).append(type).toString();
    }


}
