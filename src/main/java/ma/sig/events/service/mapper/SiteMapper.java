package ma.sig.events.service.mapper;

import ma.sig.events.domain.City;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Site;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.SiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Site} and its DTO {@link SiteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteMapper extends EntityMapper<SiteDTO, Site> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityCityId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    SiteDTO toDto(Site s);

    @Named("cityCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cityId", source = "cityId")
    CityDTO toDtoCityCityId(City city);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
