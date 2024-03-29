package ma.sig.events.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import ma.sig.events.domain.*;
import ma.sig.events.service.dto.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Accreditation} and its DTO {@link AccreditationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccreditationListMapper extends EntityMapper<AccreditationDTO, Accreditation> {
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
    @Mapping(target = "accreditationPhotoStat", expression = "java(mapAccreditationPhotoToStat(s.getAccreditationPhoto()))")
    @Mapping(target = "accreditationPhoto", ignore = true)
    @Mapping(target = "accreditationPhotoContentType", ignore = true)
    AccreditationDTO toDto(Accreditation s);

    default Boolean mapAccreditationPhotoToStat(byte[] accreditationPhoto) {
        return accreditationPhoto != null && accreditationPhoto.length > 0;
    }

    @Mapping(target = "removeSite", ignore = true)
    Accreditation toEntity(AccreditationDTO accreditationDTO);

    @Named("siteSiteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "siteId", source = "siteId")
    @Mapping(target = "siteName", source = "siteName")
    @Mapping(target = "siteStat", source = "siteStat")
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
    @Mapping(target = "categoryStat", source = "categoryStat")
    @Mapping(target = "printingModel", ignore = true)
    @Mapping(target = "printingModel.printingModelId", source = "printingModel", qualifiedByName = "extractPrintingModelId")
    CategoryDTO toDtoCategoryCategoryId(Category category);

    @Named("extractPrintingModelId")
    default Long extractPrintingModelId(PrintingModel printingModel) {
        return printingModel != null ? printingModel.getPrintingModelId() : null;
    }

    @Named("fonctionFonctionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fonctionId", source = "fonctionId")
    @Mapping(target = "fonctionName", source = "fonctionName")
    @Mapping(target = "fonctionStat", source = "fonctionStat")
    FonctionDTO toDtoFonctionFonctionId(Fonction fonction);

    @Named("organizOrganizId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "organizId", source = "organizId")
    @Mapping(target = "organizName", source = "organizName")
    @Mapping(target = "organizStat", source = "organizStat")
    OrganizDTO toDtoOrganizOrganizId(Organiz organiz);

    @Named("accreditationTypeAccreditationTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationTypeId", source = "accreditationTypeId")
    @Mapping(target = "accreditationTypeValue", source = "accreditationTypeValue")
    @Mapping(target = "accreditationTypeStat", source = "accreditationTypeStat")
    @Mapping(target = "printingModel", ignore = true)
    @Mapping(target = "printingModel.printingModelId", source = "printingModel", qualifiedByName = "extractPrintingModelId")
    AccreditationTypeDTO toDtoAccreditationTypeAccreditationTypeId(AccreditationType accreditationType);

    @Named("statusStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "statusId", source = "statusId")
    @Mapping(target = "statusName", source = "statusName")
    @Mapping(target = "statusColor", source = "statusColor")
    @Mapping(target = "statusUserCanPrint", source = "statusUserCanPrint")
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
    @Mapping(target = "codeStat", source = "codeStat")
    CodeDTO toDtoCodeCodeId(Code code);

    @Named("dayPassInfoDayPassInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "dayPassInfoId", source = "dayPassInfoId")
    @Mapping(target = "dayPassInfoName", source = "dayPassInfoName")
    @Mapping(target = "dayPassInfoStat", source = "dayPassInfoStat")
    DayPassInfoDTO toDtoDayPassInfoDayPassInfoId(DayPassInfo dayPassInfo);
}
