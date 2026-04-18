package westmeijer.oskar.weatherapi.application.ports.outbound;

import westmeijer.oskar.weatherapi.domain.model.Location;

public interface GetWeatherUpdates {

    Location requestWithGeneratedClient(Location location);

}
