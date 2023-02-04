package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "location")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    /**
     * Default constructor for Hibernate. Should never be used.
     */
    private Location() {
    }

    public Location(Integer zipCode, Integer locationCode, String cityName, String country) {
        this.zipCode = zipCode;
        this.locationCode = locationCode;
        this.cityName = cityName;
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    @Deprecated
    public Integer getLocationCode() {
        return locationCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(zipCode, location.zipCode) && Objects.equals(locationCode, location.locationCode) && Objects.equals(cityName, location.cityName) && Objects.equals(country, location.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, locationCode, cityName, country);
    }

    @Override
    public String toString() {
        return "Location{" +
                "zipCode=" + zipCode +
                ", locationCode=" + locationCode +
                ", cityName='" + cityName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
