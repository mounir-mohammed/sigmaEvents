package ma.sig.events.service.mapper;

import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Note;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.NoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "accreditation", source = "accreditation", qualifiedByName = "accreditationAccreditationId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    NoteDTO toDto(Note s);

    @Named("accreditationAccreditationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationId", source = "accreditationId")
    AccreditationDTO toDtoAccreditationAccreditationId(Accreditation accreditation);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
