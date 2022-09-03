package westmeijer.oskar.weatherapi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<String> handleNotFound(RuntimeException ex) {
        logger.error("Exception occured during lookup for request.", ex);
        String message = "Location or Weather not found!";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


}
