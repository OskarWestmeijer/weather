package westmeijer.oskar.weatherapi.util.controller;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class HealthController {

  private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

  private final MeterRegistry meterRegistry;

  public HealthController(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @GetMapping("/ping")
  public ResponseEntity<String> test() {
    meterRegistry.counter("api", "health", "ping").increment();
    return ResponseEntity.ok("pong");
  }


  @GetMapping("/memory")
  public ResponseEntity<Map<String, Long>> getMemoryStatistics() {
    long byteToLong = 1000000;
    Map<String, Long> mem = new HashMap<>();
    mem.put("TotalMem", Runtime.getRuntime().totalMemory() / byteToLong);
    mem.put("MaxMem", Runtime.getRuntime().maxMemory() / byteToLong);
    mem.put("FreeMem", Runtime.getRuntime().freeMemory() / byteToLong);
    logger.info("Memory: {}", mem.toString());
    return ResponseEntity.ok(mem);
  }

}
