package westmeijer.oskar.weatherapi.controller.model;


import java.time.Instant;

public record WeatherDto(Double temperature,
                         Integer humidity,
                         Double windSpeed,
                         Instant recordedAt) {

}
