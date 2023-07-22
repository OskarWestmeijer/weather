package westmeijer.oskar.weatherapi.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Weather representation used by the service and repository layer.
 */
@Table(name = "weather", schema = "weather")
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



  private Integer humidity;

  @Column(name = "wind_speed")
  private Double windSpeed;

  /**
   * The local zip code. Only numeric. Example: 00100
   *
   * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
   */
  @Column(name = "local_zip_code")
  @JsonIgnore
  private String localZipCode;

  @Column(name = "recorded_at")
  @CreationTimestamp(source = SourceType.DB)
  private Instant recordedAt;

  @UpdateTimestamp
  private Instant modifiedAt;

}
