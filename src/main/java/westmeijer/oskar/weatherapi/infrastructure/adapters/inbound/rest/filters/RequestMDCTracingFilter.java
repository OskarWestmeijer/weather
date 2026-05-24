package westmeijer.oskar.weatherapi.infrastructure.adapters.inbound.rest.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestMDCTracingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    ThreadContext.clearAll();

    var traceId = Optional.ofNullable(req.getHeader("X-Request-Id"))
        .orElse(generateTraceId());

    ThreadContext.put("traceId", traceId);

    try {
      log.info("Received request. method: {}, uri: {}, ip: {}",
          req.getMethod(), req.getRequestURI(), req.getHeader("X-Real-IP"));
      res.setHeader("traceId", traceId);

      chain.doFilter(request, response);
    } finally {
      ThreadContext.clearAll();
    }
  }

  private String generateTraceId() {
    return Integer.toHexString(
        ThreadLocalRandom.current().nextInt()
    );
  }

}