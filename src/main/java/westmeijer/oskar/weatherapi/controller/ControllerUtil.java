package westmeijer.oskar.weatherapi.controller;

import westmeijer.oskar.weatherapi.repository.model.LocationEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ControllerUtil {

    public static final String XML_FILE = ".xml";
    public static final String CSV_FILE = ".csv";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Creates Instant at start of the day for the associated time zone.
     *
     * @param date example: 15-07-1992
     * @param locationEntity example: Germany
     * @return example: 14-07-1992 (22:00)
     */
    public static Instant atStartOfDay(String date, LocationEntity locationEntity) {
        LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(date));
        ZoneId timezone = getTimeZone(locationEntity);
        return localDate.atStartOfDay(timezone).toInstant();
    }

    public static String buildFileName(String localZipCode, String date, String type) {
        return "weather_" + date + "_" + localZipCode + type;
    }

    private static ZoneId getTimeZone(LocationEntity locationEntity) {

        return switch (locationEntity.getCountry()) {
            case "Germany" -> ZoneId.of("Europe/Berlin");
            case "Finland" -> ZoneId.of("Europe/Helsinki");
            default -> ZoneId.of("UTC");
        };


    }


}
