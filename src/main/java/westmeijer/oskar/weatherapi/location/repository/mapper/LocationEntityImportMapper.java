package westmeijer.oskar.weatherapi.location.repository.mapper;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import westmeijer.oskar.weatherapi.location.repository.model.LocationEntity;
import westmeijer.oskar.weatherapi.location.service.model.Location;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, builder = @Builder(disableBuilder = true))
public interface LocationEntityImportMapper {

  @Named("mapToLocationEntity")
  @Mapping(target = "id", source = "locationId")
  @Mapping(target = "modifiedAt", expression = "java(java.time.Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS))")
  LocationEntity mapToLocationEntity(Location location);

  @AfterMapping
  default void setModifiedAt(@MappingTarget LocationEntity locationEntity, Location location) {
    checkArgument(CollectionUtils.size(locationEntity.getWeather()) == 1, "Only one weather record can be imported");
    locationEntity.getWeather().getFirst().setModifiedAt(Instant.now().truncatedTo(ChronoUnit.MICROS));
  }
}
