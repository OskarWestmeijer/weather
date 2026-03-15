package westmeijer.oskar.weatherapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.openapi.server.model.ProblemDetails;

import java.net.URI;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {LocationNotSupportedException.class})
    protected ResponseEntity<ProblemDetails> handleLocationNotSupported(LocationNotSupportedException ex, HttpServletRequest request) {
        logger.error("Exception occurred during weather lookup for request.", ex);
        var responseStatus = HttpStatus.NOT_FOUND;

        var problem = new ProblemDetails()
                .type(URI.create("about:blank"))
                .title("Location not found")
                .status(responseStatus.value())
                .detail("Location with id %s is not supported.".formatted(ex.getLocationId()))
                .instance(String.valueOf(URI.create(request.getRequestURI())))
                .code("LOCATION_NOT_SUPPORTED");

        return ResponseEntity.status(responseStatus)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ProblemDetails> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Unexpected exception occurred during request processing.", ex);

        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        var problem = new ProblemDetails()
                .type(URI.create("about:blank"))
                .title("Server Error")
                .status(status.value())
                .detail("The server encountered an unexpected error.")
                .instance(request.getRequestURI())
                .code("SERVER_ERROR");

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

}
