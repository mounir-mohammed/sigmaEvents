package ma.sig.events.service.mapper;

import ma.sig.events.domain.Cloning;
import ma.sig.events.service.dto.CloningDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cloning} and its DTO {@link CloningDTO}.
 */
@Mapper(componentModel = "spring")
public interface CloningMapper extends EntityMapper<CloningDTO, Cloning> {}
