package ma.sig.events.service.mapper;

import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckAccreditationHistory} and its DTO {@link CheckAccreditationHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckAccreditationHistoryMapper extends EntityMapper<CheckAccreditationHistoryDTO, CheckAccreditationHistory> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    @Mapping(target = "accreditation", source = "accreditation", qualifiedByName = "accreditationAccreditationId")
    CheckAccreditationHistoryDTO toDto(CheckAccreditationHistory s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);

    @Named("accreditationAccreditationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationId", source = "accreditationId")
    AccreditationDTO toDtoAccreditationAccreditationId(Accreditation accreditation);
}
