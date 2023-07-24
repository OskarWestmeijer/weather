package westmeijer.oskar.weatherapi.repository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
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

  /**
   * The local zip code. Only numeric. Example: 00100
   *
   * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
   */
  @Id
  private String localZipCode;

  /**
   * City id, which is maintained by OpenWeatherApi. Used to import weather from the exact location. Unique identifier. Example:
   * locationId=2875601
   *
   * @deprecated OpenWeatherApi recommends using the new Geocoding API in order to request for [lat,lon] coordinates.
   */
  @Deprecated
  private String locationCode;

  private String cityName;

  private String country;

  private Instant modifiedAt;

  private Instant lastImportAt;

}
