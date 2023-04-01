package ma.sig.events.service.mapper;

import ma.sig.events.domain.LogHistory;
import ma.sig.events.service.dto.LogHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogHistory} and its DTO {@link LogHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface LogHistoryMapper extends EntityMapper<LogHistoryDTO, LogHistory> {}
