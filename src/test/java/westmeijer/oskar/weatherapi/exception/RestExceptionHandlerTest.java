package westmeijer.oskar.weatherapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;
import westmeijer.oskar.weatherapi.openapi.server.model.ProblemDetails;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Test
    void shouldHandleLocationNotSupported() {
        // given
        Integer locationId = 1;
        LocationNotSupportedException exception = new LocationNotSupportedException(locationId);
        when(request.getRequestURI()).thenReturn("/weather");

        // when
        ResponseEntity<ProblemDetails> responseEntity =
                restExceptionHandler.handleLocationNotSupported(exception, request);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(responseEntity.getBody())
                .isNotNull()
                .returns(HttpStatus.NOT_FOUND.value(), ProblemDetails::getStatus)
                .returns(URI.create("about:blank"), ProblemDetails::getType)
                .returns("Location not found", ProblemDetails::getTitle)
                .returns("Location with id 1 is not supported.", ProblemDetails::getDetail)
                .returns("/weather", ProblemDetails::getInstance)
                .returns("LOCATION_NOT_SUPPORTED", ProblemDetails::getCode);
    }

    @Test
    void shouldHandleGeneralException() {
        // given
        RuntimeException exception = new RuntimeException();
        when(request.getRequestURI()).thenReturn("/weather");

        // when
        ResponseEntity<ProblemDetails> responseEntity =
                restExceptionHandler.handleGeneralException(exception, request);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assertThat(responseEntity.getBody())
                .isNotNull()
                .returns(HttpStatus.INTERNAL_SERVER_ERROR.value(), ProblemDetails::getStatus)
                .returns(URI.create("about:blank"), ProblemDetails::getType)
                .returns("Server Error", ProblemDetails::getTitle)
                .returns("The server encountered an unexpected error.", ProblemDetails::getDetail)
                .returns("/weather", ProblemDetails::getInstance)
                .returns("SERVER_ERROR", ProblemDetails::getCode);
    }
}