package ma.sig.events.service.mapper;

import ma.sig.events.domain.AuthentificationType;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuthentificationType} and its DTO {@link AuthentificationTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthentificationTypeMapper extends EntityMapper<AuthentificationTypeDTO, AuthentificationType> {}
