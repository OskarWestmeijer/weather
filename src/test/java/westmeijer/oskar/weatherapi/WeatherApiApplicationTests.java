package westmeijer.oskar.weatherapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import westmeijer.oskar.weatherapi.repository.database.LocationRepository;
import westmeijer.oskar.weatherapi.service.WeatherImportJob;

@ExtendWith(MockitoExtension.class)
public class WeatherApiApplicationTests {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private WeatherImportJob weatherImportJob;

    @Test
    void contextLoads() {


    }

}
