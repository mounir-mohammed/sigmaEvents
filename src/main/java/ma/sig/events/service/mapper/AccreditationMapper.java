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
    SiteDTO toDtoSiteSiteId(Site site);

    @Named("siteSiteIdSet")
    default Set<SiteDTO> toDtoSiteSiteIdSet(Set<Site> site) {
        return site.stream().map(this::toDtoSiteSiteId).collect(Collectors.toSet());
    }

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);

    @Named("civilityCivilityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "civilityId", source = "civilityId")
    CivilityDTO toDtoCivilityCivilityId(Civility civility);

    @Named("sexeSexeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "sexeId", source = "sexeId")
    SexeDTO toDtoSexeSexeId(Sexe sexe);

    @Named("nationalityNationalityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nationalityId", source = "nationalityId")
    NationalityDTO toDtoNationalityNationalityId(Nationality nationality);

    @Named("countryCountryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "countryId", source = "countryId")
    CountryDTO toDtoCountryCountryId(Country country);

    @Named("cityCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cityId", source = "cityId")
    CityDTO toDtoCityCityId(City city);

    @Named("categoryCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "categoryId", source = "categoryId")
    CategoryDTO toDtoCategoryCategoryId(Category category);

    @Named("fonctionFonctionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fonctionId", source = "fonctionId")
    FonctionDTO toDtoFonctionFonctionId(Fonction fonction);

    @Named("organizOrganizId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "organizId", source = "organizId")
    OrganizDTO toDtoOrganizOrganizId(Organiz organiz);

    @Named("accreditationTypeAccreditationTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationTypeId", source = "accreditationTypeId")
    AccreditationTypeDTO toDtoAccreditationTypeAccreditationTypeId(AccreditationType accreditationType);

    @Named("statusStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "statusId", source = "statusId")
    @Mapping(target = "statusName", source = "statusName")
    @Mapping(target = "statusColor", source = "statusColor")
    StatusDTO toDtoStatusStatusId(Status status);

    @Named("attachementAttachementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "attachementId", source = "attachementId")
    AttachementDTO toDtoAttachementAttachementId(Attachement attachement);

    @Named("codeCodeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codeId", source = "codeId")
    CodeDTO toDtoCodeCodeId(Code code);

    @Named("dayPassInfoDayPassInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "dayPassInfoId", source = "dayPassInfoId")
    DayPassInfoDTO toDtoDayPassInfoDayPassInfoId(DayPassInfo dayPassInfo);
}
