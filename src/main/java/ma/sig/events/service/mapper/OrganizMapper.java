package ma.sig.events.service.mapper;

import ma.sig.events.domain.City;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Organiz;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.dto.CountryDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.OrganizDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organiz} and its DTO {@link OrganizDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizMapper extends EntityMapper<OrganizDTO, Organiz> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryCountryId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityCityId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    OrganizDTO toDto(Organiz s);

    @Named("countryCountryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "countryId", source = "countryId")
    CountryDTO toDtoCountryCountryId(Country country);

    @Named("cityCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cityId", source = "cityId")
    CityDTO toDtoCityCityId(City city);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
