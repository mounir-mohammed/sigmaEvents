package ma.sig.events.service.mapper;

import ma.sig.events.domain.OperationType;
import ma.sig.events.service.dto.OperationTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperationType} and its DTO {@link OperationTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperationTypeMapper extends EntityMapper<OperationTypeDTO, OperationType> {}
