package westmeijer.oskar.weatherapi.weather.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;


@Table(name = "weather", schema = "weather")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class WeatherEntity {

  @Id
  private UUID id;

  private Double temperature;

  private Integer humidity;

  private Double windSpeed;

  /**
   * The local zip code. Only numeric. Example: 00100
   *
   * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
   */
  @Deprecated
  private String localZipCode;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private LocationEntity location;

  private Instant recordedAt;

  private Instant modifiedAt;

}
