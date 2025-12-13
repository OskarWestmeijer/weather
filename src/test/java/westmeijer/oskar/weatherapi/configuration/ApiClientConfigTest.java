package westmeijer.oskar.weatherapi.configuration;

import static org.mockito.Mockito.mock;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
public class ApiClientConfigTest {

  private static final String BASE_URL = "https://test-base-url.com";

  private ApiClientConfig apiClientConfig;

  @BeforeEach
  public void init() {
    apiClientConfig = new ApiClientConfig(BASE_URL);
  }

  @Test
  void shouldCreateRestClient() {
    // when
    var actualClient = apiClientConfig.externalWeatherApirestClient();

    // then
    BDDAssertions.then(actualClient)
        .isNotNull();
  }

  @Test
  void generatedOpenWeatherApi_shouldHaveCorrectBaseUrl() {
    // given
    var restClient = mock(RestClient.class);

    // when
    var actualClient = apiClientConfig.generatedOpenWeatherApi(restClient);

    // then
    BDDAssertions.then(actualClient)
        .isNotNull()
        .returns(restClient, a -> a.getApiClient().getRestClient())
        .returns(BASE_URL, a -> a.getApiClient().getBasePath());
  }

}
