package ma.sig.events.service.mapper;

import ma.sig.events.domain.PrintingType;
import ma.sig.events.service.dto.PrintingTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrintingType} and its DTO {@link PrintingTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrintingTypeMapper extends EntityMapper<PrintingTypeDTO, PrintingType> {}
