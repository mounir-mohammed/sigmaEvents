package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.PrintingModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrintingModel} and its DTO {@link PrintingModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrintingModelMapper extends EntityMapper<PrintingModelDTO, PrintingModel> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    PrintingModelDTO toDto(PrintingModel s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
