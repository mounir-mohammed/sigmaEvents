package ma.sig.events.service.mapper;

import ma.sig.events.domain.Accreditation;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PhotoArchive;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.PhotoArchiveDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhotoArchive} and its DTO {@link PhotoArchiveDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoArchiveMapper extends EntityMapper<PhotoArchiveDTO, PhotoArchive> {
    @Mapping(target = "accreditation", source = "accreditation", qualifiedByName = "accreditationAccreditationId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    PhotoArchiveDTO toDto(PhotoArchive s);

    @Named("accreditationAccreditationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accreditationId", source = "accreditationId")
    AccreditationDTO toDtoAccreditationAccreditationId(Accreditation accreditation);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
