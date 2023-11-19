package westmeijer.oskar.weatherapi.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import westmeijer.oskar.weatherapi.location.exception.LocationNotSupportedException;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

  @InjectMocks
  private RestExceptionHandler restExceptionHandler;

  @Test
  void shouldHandleLocationNotSupported() {
    Integer locationId = 1;
    LocationNotSupportedException exception = new LocationNotSupportedException(locationId);

    ResponseEntity<String> responseEntity = restExceptionHandler.handleLocationNotSupported(exception);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isEqualTo("Requested locationId not found. Please verify it is supported. locationId: 1");
  }

  @Test
  void shouldHandleGeneralException() {
    RuntimeException exception = new RuntimeException();

    ResponseEntity<String> responseEntity = restExceptionHandler.handleGeneralException(exception);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody()).isEqualTo("Exception occurred during lookup for request. Please review request.");
  }

}
