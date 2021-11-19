package westmeijer.oskar.carrentalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GarageService {

    @Autowired
    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    private GarageRepository garageRepository;

    Logger logger = LoggerFactory.getLogger(GarageService.class);

    private Collection<String> cars = List.of("Tesla", "Audi", "Mercedes", "Volkswagen");

    public List<Car> getCars() {
        List<Car> cars = garageRepository.findAll();
        logger.info("Current count of cars: {}", cars.size());
        return cars;
    }

}
