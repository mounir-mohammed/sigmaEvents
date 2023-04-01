package ma.sig.events.service.mapper;

import ma.sig.events.domain.Nationality;
import ma.sig.events.service.dto.NationalityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nationality} and its DTO {@link NationalityDTO}.
 */
@Mapper(componentModel = "spring")
public interface NationalityMapper extends EntityMapper<NationalityDTO, Nationality> {}
