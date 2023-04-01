package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.PrintingServerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrintingServer} and its DTO {@link PrintingServerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrintingServerMapper extends EntityMapper<PrintingServerDTO, PrintingServer> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    PrintingServerDTO toDto(PrintingServer s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
