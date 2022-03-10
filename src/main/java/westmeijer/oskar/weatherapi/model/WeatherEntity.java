package westmeijer.oskar.weatherapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Weather representation used by the service and repository layer.
 *
 */
@Table(name = "temperature")
@Entity
public class WeatherEntity {

    public WeatherEntity() {
    }

    protected WeatherEntity(UUID id, Long temperature, LocalDateTime timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private Long temperature;

    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getId() {
        return id;
    }

    public Long getTemperature() {
        return temperature;
    }



}
