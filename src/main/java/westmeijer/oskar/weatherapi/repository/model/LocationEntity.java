package westmeijer.oskar.weatherapi.repository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;


@Table(name = "location", schema = "weather")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocationEntity {

  /**
   * The local zip code. Only numeric. Example: 00100
   *
   * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
   */
  @Id
  @Column(name = "local_zip_code")
  private String localZipCode;

  /**
   * City id, which is maintained by OpenWeatherApi. Used to import weather from the exact location. Unique identifier. Example:
   * locationId=2875601
   *
   * @deprecated OpenWeatherApi recommends using the new Geocoding API in order to request for [lat,lon] coordinates.
   */
  @Deprecated
  @Column(name = "location_code")
  private String locationCode;

  /**
   * Full name. Example: Helsinki
   */
  @Column(name = "city_name")
  private String cityName;

  /**
   * Full name. Example: Finland
   */
  @Column(name = "country")
  private String country;

  @UpdateTimestamp
  @Setter
  private Instant modifiedAt;

  @UpdateTimestamp
  @Setter
  private Instant lastImportAt;

}
