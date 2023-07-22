package westmeijer.oskar.weatherapi.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.repository.jpa.WeatherJpaRepository;
import westmeijer.oskar.weatherapi.repository.model.WeatherEntity;

import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class WeatherExportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherJpaRepository weatherJpaRepository;

    public WeatherExportService(WeatherJpaRepository weatherJpaRepository) {
        this.weatherJpaRepository = weatherJpaRepository;
    }

    public void writeWeatherToCsv(Writer writer, String localZipCode, Instant start) {
        Instant end = start.plus(1L, ChronoUnit.DAYS);

        List<WeatherEntity> weatherEntityList = weatherJpaRepository.getSpecificDay(localZipCode, start, end);

        CSVFormat format = CSVFormat.Builder.create().setDelimiter(";").setHeader("temperature", "humidity", "wind_speed", "recorded_at_utc").build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
            for (WeatherEntity weatherEntity : weatherEntityList) {
                csvPrinter.printRecord(weatherEntity.getTemperature(), weatherEntity.getHumidity(), weatherEntity.getWindSpeed(), weatherEntity.getRecordedAt());
            }
        } catch (IOException e) {
            logger.error("Error while writing CSV.", e);
        }
    }

}
