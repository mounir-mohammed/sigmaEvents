package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.EventForm;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.EventFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventForm} and its DTO {@link EventFormDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventFormMapper extends EntityMapper<EventFormDTO, EventForm> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    EventFormDTO toDto(EventForm s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
