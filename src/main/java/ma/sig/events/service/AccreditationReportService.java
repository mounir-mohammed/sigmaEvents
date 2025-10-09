package ma.sig.events.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ma.sig.events.repository.AccreditationReportRepository;
import ma.sig.events.service.dto.AccreditationStatsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccreditationReportService {

    private final AccreditationReportRepository accreditationReportRepository;

    public AccreditationReportService(AccreditationReportRepository accreditationReportRepository) {
        this.accreditationReportRepository = accreditationReportRepository;
    }

    public AccreditationStatsDTO getAccreditationStats(Long eventId) {
        AccreditationStatsDTO dto = new AccreditationStatsDTO();

        dto.setTotalAccreditations(accreditationReportRepository.countByEventId(eventId));
        dto.setPrintedAccreditations(accreditationReportRepository.countPrintedByEventId(eventId));
        dto.setInProgressAccreditations(accreditationReportRepository.countInProgressByEventId(eventId));

        dto.setPrintedPerDay(toMap(accreditationReportRepository.countPrintedByDay(eventId)));
        dto.setStatusCounts(toMap(accreditationReportRepository.countByStatus(eventId)));
        dto.setByCategory(toMap(accreditationReportRepository.countByCategory(eventId)));
        dto.setByFunction(toMap(accreditationReportRepository.countByFunction(eventId)));
        dto.setBySite(toMap(accreditationReportRepository.countBySite(eventId)));
        dto.setByOrganization(toMap(accreditationReportRepository.countByOrganization(eventId)));
        dto.setByType(toMap(accreditationReportRepository.countByType(eventId)));
        dto.setByNationality(toMap(accreditationReportRepository.countByNationality(eventId)));

        return dto;
    }

    private Map<String, Long> toMap(List<Object[]> list) {
        if (list == null) return Map.of();
        return list
            .stream()
            .filter(arr -> arr[0] != null && arr[1] != null)
            .collect(Collectors.toMap(arr -> arr[0].toString(), arr -> ((Number) arr[1]).longValue(), (a, b) -> a, LinkedHashMap::new));
    }
}
