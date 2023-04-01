package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.OperationHistory;
import ma.sig.events.domain.OperationType;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.OperationHistoryDTO;
import ma.sig.events.service.dto.OperationTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperationHistory} and its DTO {@link OperationHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperationHistoryMapper extends EntityMapper<OperationHistoryDTO, OperationHistory> {
    @Mapping(target = "typeoperation", source = "typeoperation", qualifiedByName = "operationTypeOperationTypeId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    OperationHistoryDTO toDto(OperationHistory s);

    @Named("operationTypeOperationTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "operationTypeId", source = "operationTypeId")
    OperationTypeDTO toDtoOperationTypeOperationTypeId(OperationType operationType);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
