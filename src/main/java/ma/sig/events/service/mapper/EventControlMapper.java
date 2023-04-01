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
    EventDTO toDtoEventEventId(Event event);

    @Named("eventFieldFieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fieldId", source = "fieldId")
    EventFieldDTO toDtoEventFieldFieldId(EventField eventField);
}
