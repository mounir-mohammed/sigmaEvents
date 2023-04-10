package ma.sig.events.service.mapper;

import ma.sig.events.domain.Attachement;
import ma.sig.events.domain.AttachementType;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.AttachementDTO;
import ma.sig.events.service.dto.AttachementTypeDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachement} and its DTO {@link AttachementDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachementMapper extends EntityMapper<AttachementDTO, Attachement> {
    @Mapping(target = "attachementType", source = "attachementType", qualifiedByName = "attachementTypeAttachementTypeId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    AttachementDTO toDto(Attachement s);

    @Named("attachementTypeAttachementTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "attachementTypeId", source = "attachementTypeId")
    @Mapping(target = "attachementTypeName", source = "attachementTypeName")
    @Mapping(target = "attachementTypeStat", source = "attachementTypeStat")
    AttachementTypeDTO toDtoAttachementTypeAttachementTypeId(AttachementType attachementType);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
