package westmeijer.oskar.weatherapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import westmeijer.oskar.weatherapi.repository.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherImportJob;

@ExtendWith(MockitoExtension.class)
public class WeatherApiApplicationTests {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private WeatherImportJob weatherImportJob;

    @Test
    void contextLoads() {


    }

}
