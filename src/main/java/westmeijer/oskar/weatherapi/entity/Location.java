package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "location")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Location {

    /**
     * The local zip code. Only numeric.
     * Example: 00100
     *
     * @deprecated zip code is a poor international location matcher. Uniqueness is not guaranteed. Make use of Location.
     */
    @Id
    @Column(name = "zip_code")
    private Integer zipCode;

    /**
     * City id, which is maintained by OpenWeatherApi. Used to import weather from the exact location. Unique identifier.
     * Example: locationId=2875601
     *
     * @deprecated OpenWeatherApi recommends using the new Geocoding API in order to request for [lat,lon] coordinates.
     */
    @Deprecated
    @Column(name = "location_code")
    private Integer locationCode;

    /**
     * Full name.
     * Example: Helsinki
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * Full name.
     * Example: Finland
     */
    @Column(name = "country")
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return getZipCode() != null && Objects.equals(getZipCode(), location.getZipCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
