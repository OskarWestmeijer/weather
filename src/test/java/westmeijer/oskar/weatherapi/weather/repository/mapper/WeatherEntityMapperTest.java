package westmeijer.oskar.weatherapi.weather.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import westmeijer.oskar.weatherapi.TestWeatherFactory;
import westmeijer.oskar.weatherapi.weather.repository.model.WeatherEntity;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    WeatherEntityMapperImpl.class,
})
public class WeatherEntityMapperTest {

  @Autowired
  private WeatherEntityMapper weatherEntityMapper;

  @Test
  public void shouldMapWeather() {
    WeatherEntity weatherEntity = TestWeatherFactory.weatherEntityWithoutLocation();
    Weather weather = weatherEntityMapper.map(weatherEntity);

    assertThat(weatherEntity.getId()).isEqualTo(weather.id());
    assertThat(weatherEntity.getHumidity()).isEqualTo(weather.humidity());
    assertThat(weatherEntity.getWindSpeed()).isEqualTo(weather.windSpeed());
    assertThat(weatherEntity.getTemperature()).isEqualTo(weather.temperature());
    assertThat(weatherEntity.getRecordedAt()).isEqualTo(weather.recordedAt());
  }

  @Test
  public void shouldMapWeatherList() {
    WeatherEntity weatherEntity = TestWeatherFactory.weatherEntityWithoutLocation();

    List<Weather> actualList = weatherEntityMapper.mapList(List.of(weatherEntity));

    assertThat(actualList.size()).isEqualTo(1);
    Weather actualWeather = actualList.getFirst();
    assertThat(actualWeather)
        .returns(weatherEntity.getId(), Weather::id)
        .returns(weatherEntity.getWindSpeed(), Weather::windSpeed)
        .returns(weatherEntity.getTemperature(), Weather::temperature)
        .returns(weatherEntity.getHumidity(), Weather::humidity)
        .returns(weatherEntity.getRecordedAt(), Weather::recordedAt);
  }

}
