package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.CheckAccreditationReportRepository;
import ma.sig.events.service.CheckAccreditationReportQueryService;
import ma.sig.events.service.CheckAccreditationReportService;
import ma.sig.events.service.criteria.CheckAccreditationReportCriteria;
import ma.sig.events.service.dto.CheckAccreditationReportDTO;
import ma.sig.events.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.sig.events.domain.CheckAccreditationReport}.
 */
@RestController
@RequestMapping("/api")
public class CheckAccreditationReportResource {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationReportResource.class);

    private static final String ENTITY_NAME = "checkAccreditationReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckAccreditationReportService checkAccreditationReportService;

    private final CheckAccreditationReportRepository checkAccreditationReportRepository;

    private final CheckAccreditationReportQueryService checkAccreditationReportQueryService;

    public CheckAccreditationReportResource(
        CheckAccreditationReportService checkAccreditationReportService,
        CheckAccreditationReportRepository checkAccreditationReportRepository,
        CheckAccreditationReportQueryService checkAccreditationReportQueryService
    ) {
        this.checkAccreditationReportService = checkAccreditationReportService;
        this.checkAccreditationReportRepository = checkAccreditationReportRepository;
        this.checkAccreditationReportQueryService = checkAccreditationReportQueryService;
    }

    /**
     * {@code POST  /check-accreditation-reports} : Create a new checkAccreditationReport.
     *
     * @param checkAccreditationReportDTO the checkAccreditationReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkAccreditationReportDTO, or with status {@code 400 (Bad Request)} if the checkAccreditationReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-accreditation-reports")
    public ResponseEntity<CheckAccreditationReportDTO> createCheckAccreditationReport(
        @Valid @RequestBody CheckAccreditationReportDTO checkAccreditationReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CheckAccreditationReport : {}", checkAccreditationReportDTO);
        if (checkAccreditationReportDTO.getCheckAccreditationReportId() != null) {
            throw new BadRequestAlertException("A new checkAccreditationReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckAccreditationReportDTO result = checkAccreditationReportService.save(checkAccreditationReportDTO);
        return ResponseEntity
            .created(new URI("/api/check-accreditation-reports/" + result.getCheckAccreditationReportId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCheckAccreditationReportId().toString())
            )
            .body(result);
    }

    /**
     * {@code PUT  /check-accreditation-reports/:checkAccreditationReportId} : Updates an existing checkAccreditationReport.
     *
     * @param checkAccreditationReportId the id of the checkAccreditationReportDTO to save.
     * @param checkAccreditationReportDTO the checkAccreditationReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkAccreditationReportDTO,
     * or with status {@code 400 (Bad Request)} if the checkAccreditationReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkAccreditationReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-accreditation-reports/{checkAccreditationReportId}")
    public ResponseEntity<CheckAccreditationReportDTO> updateCheckAccreditationReport(
        @PathVariable(value = "checkAccreditationReportId", required = false) final Long checkAccreditationReportId,
        @Valid @RequestBody CheckAccreditationReportDTO checkAccreditationReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckAccreditationReport : {}, {}", checkAccreditationReportId, checkAccreditationReportDTO);
        if (checkAccreditationReportDTO.getCheckAccreditationReportId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(checkAccreditationReportId, checkAccreditationReportDTO.getCheckAccreditationReportId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkAccreditationReportRepository.existsById(checkAccreditationReportId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckAccreditationReportDTO result = checkAccreditationReportService.update(checkAccreditationReportDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    checkAccreditationReportDTO.getCheckAccreditationReportId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /check-accreditation-reports/:checkAccreditationReportId} : Partial updates given fields of an existing checkAccreditationReport, field will ignore if it is null
     *
     * @param checkAccreditationReportId the id of the checkAccreditationReportDTO to save.
     * @param checkAccreditationReportDTO the checkAccreditationReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkAccreditationReportDTO,
     * or with status {@code 400 (Bad Request)} if the checkAccreditationReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkAccreditationReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkAccreditationReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/check-accreditation-reports/{checkAccreditationReportId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<CheckAccreditationReportDTO> partialUpdateCheckAccreditationReport(
        @PathVariable(value = "checkAccreditationReportId", required = false) final Long checkAccreditationReportId,
        @NotNull @RequestBody CheckAccreditationReportDTO checkAccreditationReportDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update CheckAccreditationReport partially : {}, {}",
            checkAccreditationReportId,
            checkAccreditationReportDTO
        );
        if (checkAccreditationReportDTO.getCheckAccreditationReportId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(checkAccreditationReportId, checkAccreditationReportDTO.getCheckAccreditationReportId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkAccreditationReportRepository.existsById(checkAccreditationReportId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckAccreditationReportDTO> result = checkAccreditationReportService.partialUpdate(checkAccreditationReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                checkAccreditationReportDTO.getCheckAccreditationReportId().toString()
            )
        );
    }

    /**
     * {@code GET  /check-accreditation-reports} : get all the checkAccreditationReports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkAccreditationReports in body.
     */
    @GetMapping("/check-accreditation-reports")
    public ResponseEntity<List<CheckAccreditationReportDTO>> getAllCheckAccreditationReports(
        CheckAccreditationReportCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CheckAccreditationReports by criteria: {}", criteria);
        Page<CheckAccreditationReportDTO> page = checkAccreditationReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-accreditation-reports/count} : count all the checkAccreditationReports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/check-accreditation-reports/count")
    public ResponseEntity<Long> countCheckAccreditationReports(CheckAccreditationReportCriteria criteria) {
        log.debug("REST request to count CheckAccreditationReports by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkAccreditationReportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /check-accreditation-reports/:id} : get the "id" checkAccreditationReport.
     *
     * @param id the id of the checkAccreditationReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkAccreditationReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-accreditation-reports/{id}")
    public ResponseEntity<CheckAccreditationReportDTO> getCheckAccreditationReport(@PathVariable Long id) {
        log.debug("REST request to get CheckAccreditationReport : {}", id);
        Optional<CheckAccreditationReportDTO> checkAccreditationReportDTO = checkAccreditationReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkAccreditationReportDTO);
    }

    /**
     * {@code DELETE  /check-accreditation-reports/:id} : delete the "id" checkAccreditationReport.
     *
     * @param id the id of the checkAccreditationReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-accreditation-reports/{id}")
    public ResponseEntity<Void> deleteCheckAccreditationReport(@PathVariable Long id) {
        log.debug("REST request to delete CheckAccreditationReport : {}", id);
        checkAccreditationReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
