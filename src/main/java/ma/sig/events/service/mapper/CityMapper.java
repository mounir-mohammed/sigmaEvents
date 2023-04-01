package ma.sig.events.service.mapper;

import ma.sig.events.domain.City;
import ma.sig.events.domain.Country;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryCountryId")
    CityDTO toDto(City s);

    @Named("countryCountryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "countryId", source = "countryId")
    CountryDTO toDtoCountryCountryId(Country country);
}
