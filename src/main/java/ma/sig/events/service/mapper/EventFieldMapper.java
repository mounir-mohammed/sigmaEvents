package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventField;
import ma.sig.events.domain.EventForm;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.EventFieldDTO;
import ma.sig.events.service.dto.EventFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventField} and its DTO {@link EventFieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventFieldMapper extends EntityMapper<EventFieldDTO, EventField> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    @Mapping(target = "eventForm", source = "eventForm", qualifiedByName = "eventFormFormId")
    EventFieldDTO toDto(EventField s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);

    @Named("eventFormFormId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "formId", source = "formId")
    @Mapping(target = "formName", source = "formName")
    @Mapping(target = "formStat", source = "formStat")
    EventFormDTO toDtoEventFormFormId(EventForm eventForm);
}
