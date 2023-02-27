package westmeijer.oskar.weatherapi.openweatherapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import westmeijer.oskar.weatherapi.IntegrationTestContainers;
import westmeijer.oskar.weatherapi.entity.Location;
import westmeijer.oskar.weatherapi.entity.Weather;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiClient;
import westmeijer.oskar.weatherapi.openweatherapi.OpenWeatherApiException;

@SpringBootTest
public class OpenWeatherApiClientIT extends IntegrationTestContainers {

    @Autowired
    private OpenWeatherApiClient apiClient;

    @Test
    public void requestApiForHelsinki() {
        Location helsinki = new Location(00100, 658225, "Helsinki", "Finland");
        Weather response = apiClient.requestWeather(helsinki);

        Assertions.assertEquals(10.00, response.getTemperature());
    }

    @DisplayName("Verify that Wiremock fallback queryParam is configured correctly.")
    @Test
    public void requestApiForFallback() {
        Location fallbackLocation = new Location(000000, 000000, "Made up", "XX");
        Weather response = apiClient.requestWeather(fallbackLocation);

        Assertions.assertEquals(-5.00, response.getTemperature());
    }

    @DisplayName("Ensure error request handling is covered.")
    @Test
    public void handleErrorResponses() {
        Location error = new Location(66666, 666666, "Error", "Error");

        OpenWeatherApiException thrown = Assertions.assertThrows(OpenWeatherApiException.class, () -> {
            apiClient.requestWeather(error);
        });

        Assertions.assertEquals(OpenWeatherApiException.class, thrown.getClass());
        Assertions.assertEquals("Exception during OpenWeatherApi request.", thrown.getMessage());
    }


}
