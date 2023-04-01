package ma.sig.events.service.mapper;

import ma.sig.events.domain.Civility;
import ma.sig.events.service.dto.CivilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Civility} and its DTO {@link CivilityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CivilityMapper extends EntityMapper<CivilityDTO, Civility> {}
