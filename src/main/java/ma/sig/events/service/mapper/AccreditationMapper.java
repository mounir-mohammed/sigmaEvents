package ma.sig.events.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.City;
import ma.sig.events.domain.Civility;
import ma.sig.events.domain.Code;
import ma.sig.events.domain.Country;
import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.domain.Nationality;
import ma.sig.events.domain.Organiz;
import ma.sig.events.domain.Sexe;
import ma.sig.events.domain.Site;
import ma.sig.events.domain.Status;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import ma.sig.events.service.dto.AttachementDTO;
import ma.sig.events.service.dto.CategoryDTO;
import ma.sig.events.service.dto.CityDTO;
import ma.sig.events.service.dto.CivilityDTO;
import ma.sig.events.service.dto.CodeDTO;
import ma.sig.events.service.dto.CountryDTO;
import ma.sig.events.service.dto.DayPassInfoDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.FonctionDTO;
import ma.sig.events.service.dto.NationalityDTO;
import ma.sig.events.service.dto.OrganizDTO;
import ma.sig.events.service.dto.SexeDTO;
import ma.sig.events.service.dto.SiteDTO;
import ma.sig.events.service.dto.StatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accreditation} and its DTO {@link AccreditationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccreditationMapper extends EntityMapper<AccreditationDTO, Accreditation> {
    @Mapping(target = "sites", source = "sites", qualifiedByName = "siteSiteIdSet")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    @Mapping(target = "civility", source = "civility", qualifiedByName = "civilityCivilityId")
    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "sexeSexeId")
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "nationalityNationalityId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countryCountryId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityCityId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryCategoryId")
    @Mapping(target = "fonction", source = "fonction", qualifiedByName = "fonctionFonctionId")
    @Mapping(target = "organiz", source = "organiz", qualifiedByName = "organizOrganizId")
    @Mapping(target = "accreditationType", source = "accreditationType", qualifiedByName = "accreditationTypeAccreditationTypeId")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusStatusId")
    @Mapping(target = "attachement", source = "attachement", qualifiedByName = "attachementAttachementId")
    @Mapping(target = "code", source = "code", qualifiedByName = "codeCodeId")
    @Mapping(target = "dayPassInfo", source = "dayPassInfo", qualifiedByName = "dayPassInfoDayPassInfoId")
    AccreditationDTO toDto(Accreditation s);

    @Mapping(target = "removeSite", ignore = true)
    Accreditation toEntity(AccreditationDTO accreditationDTO);

    @Named("siteSiteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "siteId", source = "siteId")
    @Mapping(target = "siteName", source = "siteName")
    @Mapping(target = "siteAbreviation", source = "siteAbreviation")
    @Mapping(target = "siteStat", source = "siteStat")
    @Mapping(target = "siteColor", source = "siteColor")
    @Mapping(target = "siteLogo", source = "siteLogo")
    @Mapping(target = "siteLogoContentType", source = "siteLogoContentType")
    SiteDTO toDtoSiteSiteId(Site site);

    @Named("siteSiteIdSet")
    default Set<SiteDTO> toDtoSiteSiteIdSet(Set<Site> site) {
        return site.stream().map(this::toDtoSiteSiteId).collect(Collectors.toSet());
    }

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventAbreviation", source = "eventAbreviation")
    @Mapping(target = "eventStat", source = "eventStat")
    @Mapping(target = "eventPrintingModelId", source = "eventPrintingModelId")
    @Mapping(target = "eventWithPhoto", source = "eventWithPhoto")
    @Mapping(target = "eventNoCode", source = "eventNoCode")
    @Mapping(target = "eventCodeNoFilter", source = "eventCodeNoFilter")
    @Mapping(target = "eventCodeByTypeAccreditation", source = "eventCodeByTypeAccreditation")
    @Mapping(target = "eventCodeByTypeCategorie", source = "eventCodeByTypeCategorie")
    @Mapping(target = "eventCodeByTypeFonction", source = "eventCodeByTypeFonction")
    @Mapping(target = "eventCodeByTypeOrganiz", source = "eventCodeByTypeOrganiz")
    @Mapping(target = "eventDefaultPrintingLanguage", source = "eventDefaultPrintingLanguage")
    @Mapping(target = "eventPCenterPrintingLanguage", source = "eventPCenterPrintingLanguage")
    @Mapping(target = "eventLogo", source = "eventLogo")
    @Mapping(target = "eventLogoContentType", source = "eventLogoContentType")
    EventDTO toDtoEventEventId(Event event);

    @Named("civilityCivilityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "civilityId", source = "civilityId")
    @Mapping(target = "civilityValue", source = "civilityValue")
    @Mapping(target = "civilityStat", source = "civilityStat")
    CivilityDTO toDtoCivilityCivilityId(Civility civility);

    @Named("sexeSexeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "sexeId", source = "sexeId")
    @Mapping(target = "sexeValue", source = "sexeValue")
    @Mapping(target = "sexeStat", source = "sexeStat")
    SexeDTO toDtoSexeSexeId(Sexe sexe);

    @Named("nationalityNationalityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nationalityId", source = "nationalityId")
    @Mapping(target = "nationalityValue", source = "nationalityValue")
    @Mapping(target = "nationalityFlag", source = "nationalityFlag")
    @Mapping(target = "nationalityFlagContentType", source = "nationalityFlagContentType")
    @Mapping(target = "nationalityStat", source = "nationalityStat")
    NationalityDTO toDtoNationalityNationalityId(Nationality nationality);

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

    @Named("categoryCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "categoryId", source = "categoryId")
    @Mapping(target = "categoryName", source = "categoryName")
    @Mapping(target = "categoryAbreviation", source = "categoryAbreviation")
    @Mapping(target = "categoryStat", source = "categoryStat")
    @Mapping(target = "categoryColor", source = "categoryColor")
    @Mapping(target = "categoryLogo", source = "categoryLogo")
    @Mapping(target = "categoryLogoContentType", source = "categoryLogoContentType")
    @Mapping(target = "printingModel", source = "printingModel")
    CategoryDTO toDtoCategoryCategoryId(Category category);

    @Named("fonctionFonctionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fonctionId", source = "fonctionId")
    @Mapping(target = "fonctionName", source = "fonctionName")
    @Mapping(target = "fonctionAbreviation", source = "fonctionAbreviation")
    @Mapping(target = "fonctionStat", source = "fonctionStat")
    @Mapping(target = "fonctionColor", source = "fonctionColor")
    @Mapping(target = "fonctionLogo", source = "fonctionLogo")
    @Mapping(target = "fonctionLogoContentType", source = "fonctionLogoContentType")
    @Mapping(target = "areas", source = "areas")
    FonctionDTO toDtoFonctionFonctionId(Fonction fonction);

    @Named("organizOrganizId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "organizId", source = "organizId")
    @Mapping(target = "organizName", source = "organizName")
    @Mapping(target = "organizStat", source = "organizStat")
    @Mapping(target = "organizLogo", source = "organizLogo")
    @Mapping(target = "organizLogoContentType", source = "organizLogoContentType")
    OrganizDTO toDtoOrganizOrganizId(Organiz organiz);

    @Named("accreditationTypeAccreditationTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationTypeId", source = "accreditationTypeId")
    @Mapping(target = "accreditationTypeValue", source = "accreditationTypeValue")
    @Mapping(target = "accreditationTypeStat", source = "accreditationTypeStat")
    @Mapping(target = "printingModel", source = "printingModel")
    AccreditationTypeDTO toDtoAccreditationTypeAccreditationTypeId(AccreditationType accreditationType);

    @Named("statusStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "statusId", source = "statusId")
    @Mapping(target = "statusName", source = "statusName")
    @Mapping(target = "statusColor", source = "statusColor")
    @Mapping(target = "statusStat", source = "statusStat")
    StatusDTO toDtoStatusStatusId(Status status);

    @Named("attachementAttachementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "attachementId", source = "attachementId")
    @Mapping(target = "attachementName", source = "attachementName")
    @Mapping(target = "attachementStat", source = "attachementStat")
    AttachementDTO toDtoAttachementAttachementId(Attachement attachement);

    @Named("codeCodeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codeId", source = "codeId")
    @Mapping(target = "codeValue", source = "codeValue")
    @Mapping(target = "codeUsed", source = "codeUsed")
    @Mapping(target = "codeType", source = "codeType")
    @Mapping(target = "codeStat", source = "codeStat")
    CodeDTO toDtoCodeCodeId(Code code);

    @Named("dayPassInfoDayPassInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "dayPassInfoId", source = "dayPassInfoId")
    @Mapping(target = "dayPassInfoName", source = "dayPassInfoName")
    @Mapping(target = "dayPassInfoStat", source = "dayPassInfoStat")
    DayPassInfoDTO toDtoDayPassInfoDayPassInfoId(DayPassInfo dayPassInfo);
}
