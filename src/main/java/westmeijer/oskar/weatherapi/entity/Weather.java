package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 * Weather representation used by the service and repository layer.
 */
@Table(name = "weather")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private Double temperature;

    /**
     * Timezone=UTC.
     */
    @Column(name = "recorded_at")
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
    @Column(name = "local_zip_code")
    @JsonIgnore
    private String localZipCode;

}
