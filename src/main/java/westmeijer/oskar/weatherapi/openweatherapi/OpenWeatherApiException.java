package westmeijer.oskar.weatherapi.openweatherapi;

public class OpenWeatherApiException extends RuntimeException {
    public OpenWeatherApiException(String msg, Exception e) {
        super(msg, e);
    }
}
