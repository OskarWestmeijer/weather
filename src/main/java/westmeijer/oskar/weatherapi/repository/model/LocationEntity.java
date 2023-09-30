package westmeijer.oskar.weatherapi.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "location", schema = "weather")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LocationEntity {

  @Id
  private Integer id;

  private UUID uuid;

  private String localZipCode;

  private String openWeatherApiLocationCode;

  private String cityName;

  private String country;

  private String countryCode;

  private Instant lastImportAt;

  private Instant modifiedAt;

  private Instant createdAt;

}
