package westmeijer.oskar.weatherapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.openapi.server.model.ProblemDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {LocationNotSupportedException.class})
    protected ResponseEntity<ProblemDetails> handleLocationNotSupported(LocationNotSupportedException ex) {
        logger.error("Exception occurred during weather lookup for request.", ex);
        String message = String.format("Requested locationId not found. Please verify it is supported. locationId: %s", ex.getLocationId());
        var problem = new ProblemDetails();
        problem.setTitle("Location not known");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<String> handleGeneralException(RuntimeException ex) {
        logger.error("Exception occurred during lookup for request.", ex);
        String message = "Exception occurred during lookup for request. Please review request.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }


}
