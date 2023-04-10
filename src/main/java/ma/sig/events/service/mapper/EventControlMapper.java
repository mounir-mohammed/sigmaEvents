package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventControl;
import ma.sig.events.domain.EventField;
import ma.sig.events.service.dto.EventControlDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.EventFieldDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventControl} and its DTO {@link EventControlDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventControlMapper extends EntityMapper<EventControlDTO, EventControl> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    @Mapping(target = "eventField", source = "eventField", qualifiedByName = "eventFieldFieldId")
    EventControlDTO toDto(EventControl s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);

    @Named("eventFieldFieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fieldId", source = "fieldId")
    @Mapping(target = "fieldName", source = "fieldName")
    @Mapping(target = "fieldStat", source = "fieldStat")
    EventFieldDTO toDtoEventFieldFieldId(EventField eventField);
}
