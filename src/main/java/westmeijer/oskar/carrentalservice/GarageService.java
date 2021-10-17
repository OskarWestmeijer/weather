package westmeijer.oskar.carrentalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GarageService {

    Logger logger = LoggerFactory.getLogger(GarageService.class);

    private Collection<String> cars = List.of("Tesla", "Audi", "Mercedes", "Volkswagen");

    public Collection<String> getCars() {
        logger.info("Current count of cars: {}", cars.size());
        return cars;
    }

}
