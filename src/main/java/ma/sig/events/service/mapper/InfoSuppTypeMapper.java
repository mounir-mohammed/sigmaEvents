package ma.sig.events.service.mapper;

import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InfoSuppType} and its DTO {@link InfoSuppTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface InfoSuppTypeMapper extends EntityMapper<InfoSuppTypeDTO, InfoSuppType> {}
