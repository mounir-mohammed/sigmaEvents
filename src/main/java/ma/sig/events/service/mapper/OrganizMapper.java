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
    @Mapping(target = "countryName", source = "countryName")
    @Mapping(target = "countryStat", source = "countryStat")
    CountryDTO toDtoCountryCountryId(Country country);

    @Named("cityCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cityId", source = "cityId")
    @Mapping(target = "cityName", source = "cityName")
    @Mapping(target = "cityStat", source = "cityStat")
    CityDTO toDtoCityCityId(City city);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
