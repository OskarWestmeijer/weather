package westmeijer.oskar.weatherapi.entity;

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
public class Weather {

    /**
     * Default constructor for Hibernate. Should never be used.
     */
    private Weather() {
    }

    public Weather(UUID id, double temperature, LocalDateTime timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private double temperature;

    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }



}