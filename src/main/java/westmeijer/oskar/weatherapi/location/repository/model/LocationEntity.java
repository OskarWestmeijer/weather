package westmeijer.oskar.weatherapi.location.repository.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;


@Table(name = "location")
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

  @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<WeatherEntity> weather = new ArrayList<>();

  public void addWeather(WeatherEntity weatherEntity) {
    this.weather.add(weatherEntity);
    weatherEntity.setLocation(this);
  }

}
