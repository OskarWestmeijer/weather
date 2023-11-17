package westmeijer.oskar.weatherapi.location.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;


@Table(name = "location", schema = "weather")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LocationEntity {

  @Id
  private Integer id;

  private UUID uuid;

  private String localZipCode;

  private String openWeatherApiLocationCode;

  private String cityName;

  @Deprecated
  private String country;

  private String countryCode;

  private String latitude;

  private String longitude;

  private Instant lastImportAt;

  private Instant modifiedAt;

  private Instant createdAt;

  @OneToMany(mappedBy = "location")
  private List<WeatherEntity> weather = new ArrayList<>();

  public void addWeather(WeatherEntity weatherEntity) {
    this.weather.add(weatherEntity);
    weatherEntity.setLocation(this);
  }

}
