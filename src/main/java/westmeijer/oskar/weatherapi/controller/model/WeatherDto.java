package westmeijer.oskar.weatherapi.controller.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WeatherDto(@JsonIgnore UUID id,
                         Double temperature,
                         Integer humidity,
                         Double windSpeed,
                         @JsonIgnore String localZipCode,
                         Instant recordedAt) {

}
