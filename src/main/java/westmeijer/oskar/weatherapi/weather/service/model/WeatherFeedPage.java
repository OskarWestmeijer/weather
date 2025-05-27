package westmeijer.oskar.weatherapi.weather.service.model;

import java.time.Instant;
import java.util.List;
import lombok.Builder;

@Builder
public record WeatherFeedPage(List<Weather> weatherList,
                              PagingDetails pagingDetails) {

  @Builder
  public record PagingDetails(Instant nextFrom,
                              String nextLink,
                              Integer totalRecords,
                              Integer pageRecords,
                              boolean hasNewerRecords) {

  }

}
