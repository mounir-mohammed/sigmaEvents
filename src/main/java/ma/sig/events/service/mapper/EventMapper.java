package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.Language;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.LanguageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "language", source = "language", qualifiedByName = "languageLanguageId")
    EventDTO toDto(Event s);

    @Named("languageLanguageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "languageId", source = "languageId")
    @Mapping(target = "languageCode", source = "languageCode")
    @Mapping(target = "languageName", source = "languageName")
    @Mapping(target = "languageStat", source = "languageStat")
    LanguageDTO toDtoLanguageLanguageId(Language language);
}
