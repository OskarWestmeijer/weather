package westmeijer.oskar.weatherapi.repository.openweatherapi;

public class OpenWeatherApiException extends RuntimeException {
    public OpenWeatherApiException(String msg, Exception e) {
        super(msg, e);
    }
}
