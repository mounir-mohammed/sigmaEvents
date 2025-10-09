package ma.sig.events.web.rest;

import java.util.Optional;
import ma.sig.events.service.AccreditationReportService;
import ma.sig.events.service.dto.AccreditationStatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for generating accreditation reports.
 */
@RestController
@RequestMapping("/api")
public class AccreditationReportResource {

    private final Logger log = LoggerFactory.getLogger(AccreditationReportResource.class);

    private final AccreditationReportService reportService;

    public AccreditationReportResource(AccreditationReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * {@code GET  /reports/accreditations/:eventId} : get statistics for the given event.
     *
     * @param eventId the id of the event to retrieve report data for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the body containing the report data,
     * or {@code 404 (Not Found)} if no event exists.
     */
    @GetMapping("/reports/accreditations/{eventId}")
    public ResponseEntity<AccreditationStatsDTO> getAccreditationStats(@PathVariable Long eventId) {
        log.debug("REST request to get Accreditation Report for Event : {}", eventId);
        Optional<AccreditationStatsDTO> dto = Optional.ofNullable(reportService.getAccreditationStats(eventId));
        return ResponseUtil.wrapOrNotFound(dto);
    }
}
