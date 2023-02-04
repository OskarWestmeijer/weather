package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Weather representation used by the service and repository layer.
 */
@Table(name = "weather")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Weather {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private Double temperature;

    /**
     * Timezone=UTC.
     */
    @Column(name = "timestamp")
    private Instant recordedAt;

    private Integer humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    /**
     * The local zip code. Only numeric.
     * Example: 00100
     *
     * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
     */
    @Column(name = "zip_code")
    @JsonIgnore
    private Integer zipCode;

    /**
     * Default constructor for Hibernate. Should never be used.
     */
    private Weather() {
    }

    public Weather(UUID id, Double temperature, Instant recordedAt, Integer humidity, Double windSpeed, Integer zipCode) {
        this.id = id;
        this.temperature = temperature;
        this.recordedAt = recordedAt;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.zipCode = zipCode;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public UUID getId() {
        return id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(id, weather.id) && Objects.equals(temperature, weather.temperature) && Objects.equals(recordedAt, weather.recordedAt) && Objects.equals(humidity, weather.humidity) && Objects.equals(windSpeed, weather.windSpeed) && Objects.equals(zipCode, weather.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, recordedAt, humidity, windSpeed, zipCode);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", recordedAt=" + recordedAt +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", zipCode=" + zipCode +
                '}';
    }
}
