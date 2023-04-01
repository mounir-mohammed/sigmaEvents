package ma.sig.events.service.mapper;

import ma.sig.events.domain.AttachementType;
import ma.sig.events.service.dto.AttachementTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachementType} and its DTO {@link AttachementTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachementTypeMapper extends EntityMapper<AttachementTypeDTO, AttachementType> {}
