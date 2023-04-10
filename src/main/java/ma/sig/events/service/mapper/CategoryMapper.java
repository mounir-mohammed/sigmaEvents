package ma.sig.events.service.mapper;

import ma.sig.events.domain.Category;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingModel;
import ma.sig.events.service.dto.CategoryDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.PrintingModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "printingModel", source = "printingModel", qualifiedByName = "printingModelPrintingModelId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    CategoryDTO toDto(Category s);

    @Named("printingModelPrintingModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "printingModelId", source = "printingModelId")
    @Mapping(target = "printingModelName", source = "printingModelName")
    @Mapping(target = "printingModelStat", source = "printingModelStat")
    PrintingModelDTO toDtoPrintingModelPrintingModelId(PrintingModel printingModel);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
