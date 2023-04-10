package ma.sig.events.service.mapper;

import ma.sig.events.domain.Event;
import ma.sig.events.domain.Language;
import ma.sig.events.domain.Setting;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.LanguageDTO;
import ma.sig.events.service.dto.SettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Setting} and its DTO {@link SettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface SettingMapper extends EntityMapper<SettingDTO, Setting> {
    @Mapping(target = "language", source = "language", qualifiedByName = "languageLanguageId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    SettingDTO toDto(Setting s);

    @Named("languageLanguageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "languageId", source = "languageId")
    @Mapping(target = "languageName", source = "languageName")
    @Mapping(target = "languageStat", source = "languageStat")
    LanguageDTO toDtoLanguageLanguageId(Language language);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);
}
