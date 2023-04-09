package ma.sig.events.service.mapper;

import ma.sig.events.domain.City;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Language;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.domain.PrintingType;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.dto.CountryDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.LanguageDTO;
import ma.sig.events.service.dto.OrganizDTO;
import ma.sig.events.service.dto.PrintingCentreDTO;
import ma.sig.events.service.dto.PrintingModelDTO;
import ma.sig.events.service.dto.PrintingServerDTO;
import ma.sig.events.service.dto.PrintingTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrintingCentre} and its DTO {@link PrintingCentreDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrintingCentreMapper extends EntityMapper<PrintingCentreDTO, PrintingCentre> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityCityId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryCountryId")
    @Mapping(target = "organiz", source = "organiz", qualifiedByName = "organizOrganizId")
    @Mapping(target = "printingType", source = "printingType", qualifiedByName = "printingTypePrintingTypeId")
    @Mapping(target = "printingServer", source = "printingServer", qualifiedByName = "printingServerPrintingServerId")
    @Mapping(target = "printingModel", source = "printingModel", qualifiedByName = "printingModelPrintingModelId")
    @Mapping(target = "language", source = "language", qualifiedByName = "languageLanguageId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    PrintingCentreDTO toDto(PrintingCentre s);

    @Named("cityCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cityId", source = "cityId")
    @Mapping(target = "cityName", source = "cityName")
    @Mapping(target = "cityStat", source = "cityStat")
    CityDTO toDtoCityCityId(City city);

    @Named("countryCountryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "countryId", source = "countryId")
    @Mapping(target = "countryName", source = "countryName")
    @Mapping(target = "countryStat", source = "countryStat")
    CountryDTO toDtoCountryCountryId(Country country);

    @Named("organizOrganizId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "organizId", source = "organizId")
    @Mapping(target = "organizName", source = "organizName")
    @Mapping(target = "organizStat", source = "organizStat")
    OrganizDTO toDtoOrganizOrganizId(Organiz organiz);

    @Named("printingTypePrintingTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "printingTypeId", source = "printingTypeId")
    @Mapping(target = "printingTypeValue", source = "printingTypeValue")
    @Mapping(target = "printingTypeStat", source = "printingTypeStat")
    PrintingTypeDTO toDtoPrintingTypePrintingTypeId(PrintingType printingType);

    @Named("printingServerPrintingServerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "printingServerId", source = "printingServerId")
    @Mapping(target = "printingServerName", source = "printingServerName")
    @Mapping(target = "printingServerStat", source = "printingServerStat")
    PrintingServerDTO toDtoPrintingServerPrintingServerId(PrintingServer printingServer);

    @Named("printingModelPrintingModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "printingModelId", source = "printingModelId")
    @Mapping(target = "printingModelName", source = "printingModelName")
    @Mapping(target = "printingModelStat", source = "printingModelStat")
    PrintingModelDTO toDtoPrintingModelPrintingModelId(PrintingModel printingModel);

    @Named("languageLanguageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "languageId", source = "languageId")
    @Mapping(target = "languageName", source = "languageName")
    @Mapping(target = "languageStat", source = "languageStat")
    LanguageDTO toDtoLanguageLanguageId(Language language);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
