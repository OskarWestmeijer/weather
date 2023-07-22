package westmeijer.oskar.weatherapi.controller.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WeatherDto(@JsonIgnore UUID id, Double temperature, Instant recordedAt, Integer humidity,
                         Double windSpeed,
                         @JsonIgnore String localZipCode) {

    public WeatherDto(UUID id, Double temperature, Instant recordedAt, Integer humidity,
                      Double windSpeed, String localZipCode) {
        this.id = id;
        this.temperature = temperature;
        this.recordedAt = recordedAt.truncatedTo(ChronoUnit.SECONDS);
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.localZipCode = localZipCode;
    }

}
