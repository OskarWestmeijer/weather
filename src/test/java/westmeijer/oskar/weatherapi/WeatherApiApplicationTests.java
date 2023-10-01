package westmeijer.oskar.weatherapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.repository.jpa.LocationJpaRepository;
import westmeijer.oskar.weatherapi.importjob.WeatherImportJob;

@ExtendWith(MockitoExtension.class)
public class WeatherApiApplicationTests {

    @Mock
    private LocationJpaRepository locationJpaRepository;

    @Mock
    private WeatherImportJob weatherImportJob;

    @Test
    void contextLoads() {


    }

}
