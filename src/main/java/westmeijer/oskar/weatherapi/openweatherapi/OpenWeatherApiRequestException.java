package westmeijer.oskar.weatherapi.openweatherapi;

public class OpenWeatherApiRequestException extends RuntimeException {
    public OpenWeatherApiRequestException(String msg, Exception e) {
        super(msg, e);
    }
}
