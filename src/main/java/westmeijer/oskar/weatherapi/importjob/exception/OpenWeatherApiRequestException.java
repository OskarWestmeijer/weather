package westmeijer.oskar.weatherapi.importjob.exception;

public class OpenWeatherApiRequestException extends RuntimeException {

  public OpenWeatherApiRequestException(String msg, Exception e) {
    super(msg, e);
  }
}
