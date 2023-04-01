package ma.sig.events.service.mapper;

import ma.sig.events.domain.CodeType;
import ma.sig.events.service.dto.CodeTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CodeType} and its DTO {@link CodeTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CodeTypeMapper extends EntityMapper<CodeTypeDTO, CodeType> {}
