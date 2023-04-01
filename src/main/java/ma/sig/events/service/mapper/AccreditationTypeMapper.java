package ma.sig.events.service.mapper;

import ma.sig.events.domain.AccreditationType;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.service.dto.AccreditationTypeDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.PrintingModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccreditationType} and its DTO {@link AccreditationTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccreditationTypeMapper extends EntityMapper<AccreditationTypeDTO, AccreditationType> {
    @Mapping(target = "printingModel", source = "printingModel", qualifiedByName = "printingModelPrintingModelId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    AccreditationTypeDTO toDto(AccreditationType s);

    @Named("printingModelPrintingModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "printingModelId", source = "printingModelId")
    PrintingModelDTO toDtoPrintingModelPrintingModelId(PrintingModel printingModel);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
