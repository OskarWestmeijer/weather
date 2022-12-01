package westmeijer.oskar.weatherapi.repository.openweatherapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

import java.io.IOException;

@JsonTest
public class OpenWeatherApiResponseTest {

    @Autowired
    private JacksonTester<OpenWeatherApiResponse> tester;

    @Value("classpath:openweatherapi/luebeck_response.json")
    private Resource jsonResponse;


    @Test
    public void deserializeToObject() throws IOException {

        OpenWeatherApiResponse response = tester.read(jsonResponse).getObject();

        Assertions.assertEquals(99.53d, response.getMain().getTemperature());
        Assertions.assertEquals(2.57d, response.getWind().getWindSpeed());
        Assertions.assertEquals(85, response.getMain().getHumidity());
    }

}
