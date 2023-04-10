package ma.sig.events.service.mapper;

import ma.sig.events.domain.CheckAccreditationHistory;
import ma.sig.events.domain.CheckAccreditationReport;
import ma.sig.events.domain.Event;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
import ma.sig.events.service.dto.CheckAccreditationReportDTO;
import ma.sig.events.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckAccreditationReport} and its DTO {@link CheckAccreditationReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckAccreditationReportMapper extends EntityMapper<CheckAccreditationReportDTO, CheckAccreditationReport> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    @Mapping(
        target = "checkAccreditationHistory",
        source = "checkAccreditationHistory",
        qualifiedByName = "checkAccreditationHistoryCheckAccreditationHistoryId"
    )
    CheckAccreditationReportDTO toDto(CheckAccreditationReport s);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "eventStat", source = "eventStat")
    EventDTO toDtoEventEventId(Event event);

    @Named("checkAccreditationHistoryCheckAccreditationHistoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "checkAccreditationHistoryId", source = "checkAccreditationHistoryId")
    CheckAccreditationHistoryDTO toDtoCheckAccreditationHistoryCheckAccreditationHistoryId(
        CheckAccreditationHistory checkAccreditationHistory
    );
}
