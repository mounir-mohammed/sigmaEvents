package ma.sig.events.service.mapper;

import ma.sig.events.domain.Code;
import ma.sig.events.domain.CodeType;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.CodeDTO;
import ma.sig.events.service.dto.CodeTypeDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Code} and its DTO {@link CodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CodeMapper extends EntityMapper<CodeDTO, Code> {
    @Mapping(target = "codeType", source = "codeType", qualifiedByName = "codeTypeCodeTypeId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    CodeDTO toDto(Code s);

    @Named("codeTypeCodeTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codeTypeId", source = "codeTypeId")
    CodeTypeDTO toDtoCodeTypeCodeTypeId(CodeType codeType);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
