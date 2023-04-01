package ma.sig.events.service.mapper;

import ma.sig.events.domain.DayPassInfo;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.DayPassInfoDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DayPassInfo} and its DTO {@link DayPassInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DayPassInfoMapper extends EntityMapper<DayPassInfoDTO, DayPassInfo> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    DayPassInfoDTO toDto(DayPassInfo s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
