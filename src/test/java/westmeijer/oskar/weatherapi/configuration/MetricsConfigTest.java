package westmeijer.oskar.weatherapi.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MetricsConfigTest {

  @Mock
  private MeterRegistry meterRegistry;

  @InjectMocks
  private MetricsConfig metricsConfig;

  @Test
  void shouldInitMeterRegistry() {
    MeterRegistry actualRegistry = metricsConfig.init();

    assertThat(actualRegistry).isNotNull();
    assertThat(actualRegistry.getClass()).isEqualTo(SimpleMeterRegistry.class);
  }
}

