package westmeijer.oskar.weatherapi.openweatherapi.model;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;


@JsonTest
public class OpenWeatherApiResponseJsonTestEntity {

    @Autowired
    private JacksonTester<OpenWeatherApiResponse> tester;

    @Value("classpath:openweatherapi/luebeck_response.json")
    private Resource validJsonResponse;

    @Value("classpath:openweatherapi/luebeck_response_humidity_missing.json")
    private Resource invalidJsonResponse;


    @Test
    @SneakyThrows
    public void deserializeToObject() {

        OpenWeatherApiResponse response = tester.read(validJsonResponse).getObject();

        Assertions.assertEquals(99.53d, response.main().temperature());
        Assertions.assertEquals(2.57d, response.wind().windSpeed());
        Assertions.assertEquals(85, response.main().humidity());
    }

    @Test
    @SneakyThrows
    public void requireNonNull() {

        MismatchedInputException thrown = Assertions.assertThrows(MismatchedInputException.class, () -> {
            tester.read(invalidJsonResponse).getObject();
        });

        Assertions.assertEquals(MismatchedInputException.class, thrown.getClass());
    }

}
