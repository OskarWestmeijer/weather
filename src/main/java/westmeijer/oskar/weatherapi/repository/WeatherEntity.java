package westmeijer.oskar.weatherapi.repository;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "temperature")
@Entity
public class WeatherEntity {

    public WeatherEntity() {

    }

    public WeatherEntity(UUID id, Long temperature) {
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
