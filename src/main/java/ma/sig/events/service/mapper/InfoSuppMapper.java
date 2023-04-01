package ma.sig.events.service.mapper;

import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.InfoSupp;
import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.InfoSuppDTO;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InfoSupp} and its DTO {@link InfoSuppDTO}.
 */
@Mapper(componentModel = "spring")
public interface InfoSuppMapper extends EntityMapper<InfoSuppDTO, InfoSupp> {
    @Mapping(target = "infoSuppType", source = "infoSuppType", qualifiedByName = "infoSuppTypeInfoSuppTypeId")
    @Mapping(target = "accreditation", source = "accreditation", qualifiedByName = "accreditationAccreditationId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    InfoSuppDTO toDto(InfoSupp s);

    @Named("infoSuppTypeInfoSuppTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "infoSuppTypeId", source = "infoSuppTypeId")
    InfoSuppTypeDTO toDtoInfoSuppTypeInfoSuppTypeId(InfoSuppType infoSuppType);

    @Named("accreditationAccreditationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationId", source = "accreditationId")
    AccreditationDTO toDtoAccreditationAccreditationId(Accreditation accreditation);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
