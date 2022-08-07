package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

/**
 * Weather representation used by the service and repository layer.
 */
@Table(name = "temperature")
@Entity
public class Weather {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private double temperature;

    @Column(name = "timestamp")
    private Instant recordedAt;

    /**
     * Default constructor for Hibernate. Should never be used.
     */
    private Weather() {
    }

    public Weather(UUID id, double temperature, Instant timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.recordedAt = timestamp.truncatedTo(ChronoUnit.SECONDS);
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public UUID getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setRecordedAt(Instant recordedAt) {
        this.recordedAt = recordedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(weather.temperature, temperature) == 0 && Objects.equals(id, weather.id) && Objects.equals(recordedAt, weather.recordedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, recordedAt);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", timestamp=" + recordedAt +
                '}';
    }
}
