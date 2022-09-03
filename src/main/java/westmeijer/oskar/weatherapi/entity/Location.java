package westmeijer.oskar.weatherapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "location")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

    @Id
    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "location_code")
    private Integer locationCode;

    @Column(name = "city_name")
    private String cityName;

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
