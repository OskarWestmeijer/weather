package westmeijer.oskar.weatherapi.controller.model;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class WeatherDTOJsonTest {

    @Autowired
    private JacksonTester<WeatherDTO> tester;

    @Test
    @SneakyThrows
    public void serializeToJson() {
        UUID uuid = UUID.randomUUID();
        Instant registered = Instant.now();
        WeatherDTO weatherDTO = new WeatherDTO(UUID.randomUUID(), 22.54d, Instant.now(), 34, 89.12d, 1234);

        JsonContent<WeatherDTO> json = tester.write(weatherDTO);

        assertThat(json).doesNotHaveJsonPath("id");
        assertThat(json.getJson()).isEqualTo("""
                {"temperature":%s,"recordedAt":"%s","humidity":%s,"windSpeed":%s}"""
                .formatted(weatherDTO.temperature(), registered.truncatedTo(ChronoUnit.SECONDS), weatherDTO.humidity(), weatherDTO.windSpeed()));

    }

}
