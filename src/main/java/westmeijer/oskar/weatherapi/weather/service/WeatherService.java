package westmeijer.oskar.weatherapi.weather.service;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import westmeijer.oskar.weatherapi.weather.repository.WeatherRepository;
import westmeijer.oskar.weatherapi.weather.service.model.Weather;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage;
import westmeijer.oskar.weatherapi.weather.service.model.WeatherFeedPage.PagingDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherRepository weatherRepository;

  public WeatherFeedPage getWeatherFeedPage(Integer locationId, Instant from, Integer limit) {
    Instant fromTimestamp = Optional.ofNullable(from).orElse(Instant.EPOCH);
    int resultLimit = Optional.ofNullable(limit).orElse(1000);

    var weatherList = weatherRepository.getWeather(locationId, fromTimestamp, resultLimit + 1);
    var pagingDetails = getPagingDetails(locationId, weatherList, fromTimestamp, resultLimit);

    return WeatherFeedPage.builder()
        .weatherList(weatherList)
        .pagingDetails(pagingDetails)
        .build();
  }

  private PagingDetails getPagingDetails(Integer locationId, List<Weather> weatherList, Instant fromTimestamp, Integer resultLimit) {
    var totalRecordsCount = 0;
    boolean hasNewerRecords = false;
    Instant nextFrom = Instant.now();

    if (!weatherList.isEmpty()) {
      totalRecordsCount = getTotalCount(locationId, fromTimestamp);

      if (weatherList.size() > resultLimit) {
        nextFrom = weatherList.getLast().recordedAt();
        weatherList.removeLast();
        hasNewerRecords = true;
      }
    }

    String nextLink = "https://api.weather.oskar-westmeijer.com/weather?locationId=%s&from=%s&limit=%d".formatted(locationId, nextFrom,
        resultLimit);

    return PagingDetails.builder()
        .nextFrom(nextFrom)
        .nextLink(nextLink)
        .hasNewerRecords(hasNewerRecords)
        .totalRecords(totalRecordsCount)
        .pageRecords(weatherList.size())
        .build();
  }

  public int getTotalCount(Integer locationId, Instant from) {
    requireNonNull(locationId, "locationId is required");
    requireNonNull(from, "from is required");
    return weatherRepository.getTotalCount(locationId, from);
  }

}
