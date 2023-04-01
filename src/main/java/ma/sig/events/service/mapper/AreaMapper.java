package ma.sig.events.service.mapper;

import ma.sig.events.domain.Area;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.AreaDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Area} and its DTO {@link AreaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaMapper extends EntityMapper<AreaDTO, Area> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    AreaDTO toDto(Area s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
