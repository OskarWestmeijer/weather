package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.filters;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

public class RequestMDCTracingFilterTest {

  private RequestMDCTracingFilter filter;

  @BeforeEach
  void setUp() {
    filter = new RequestMDCTracingFilter();
  }

  @Test
  void should_add_traceId_header_and_cleanup_mdc() throws Exception {

    // given
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);

    given(request.getMethod()).willReturn("GET");
    given(request.getRequestURI()).willReturn("/test");
    given(request.getRemoteAddr()).willReturn("127.0.0.1");

    // when
    filter.doFilter(request, response, chain);

    // then
    then(ThreadContext.get("traceId")).isNull(); // MDC cleaned up

    BDDMockito.then(response)
        .should()
        .setHeader(eq("traceId"), anyString());

    BDDMockito.then(chain)
        .should(times(1))
        .doFilter(request, response);
  }

}
