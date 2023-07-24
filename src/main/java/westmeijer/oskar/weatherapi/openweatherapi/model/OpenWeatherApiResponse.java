package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public record OpenWeatherApiResponse(@JsonProperty(value = "main", required = true) Main main,
                                     @JsonProperty(value = "wind", required = true) Wind wind) {

  record Main(@JsonProperty(value = "temp", required = true) double temperature,
              @JsonProperty(value = "humidity", required = true) int humidity) {

  }

  record Wind(@JsonProperty(value = "speed", required = true) double windSpeed) {

  }

}
