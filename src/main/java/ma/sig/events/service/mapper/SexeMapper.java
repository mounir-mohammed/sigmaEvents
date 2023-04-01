package ma.sig.events.service.mapper;

import ma.sig.events.domain.Sexe;
import ma.sig.events.service.dto.SexeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sexe} and its DTO {@link SexeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SexeMapper extends EntityMapper<SexeDTO, Sexe> {}
