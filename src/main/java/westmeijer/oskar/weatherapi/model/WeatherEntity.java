package westmeijer.oskar.weatherapi.model;

import javax.persistence.*;
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

    protected WeatherEntity(UUID id, Long temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private Long temperature;

    public UUID getId() {
        return id;
    }

    public Long getTemperature() {
        return temperature;
    }



}
