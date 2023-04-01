package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.sig.events.repository.CheckAccreditationHistoryRepository;
import ma.sig.events.service.CheckAccreditationHistoryQueryService;
import ma.sig.events.service.CheckAccreditationHistoryService;
import ma.sig.events.service.criteria.CheckAccreditationHistoryCriteria;
import ma.sig.events.service.dto.CheckAccreditationHistoryDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.CheckAccreditationHistory}.
 */
@RestController
@RequestMapping("/api")
public class CheckAccreditationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CheckAccreditationHistoryResource.class);

    private static final String ENTITY_NAME = "checkAccreditationHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckAccreditationHistoryService checkAccreditationHistoryService;

    private final CheckAccreditationHistoryRepository checkAccreditationHistoryRepository;

    private final CheckAccreditationHistoryQueryService checkAccreditationHistoryQueryService;

    public CheckAccreditationHistoryResource(
        CheckAccreditationHistoryService checkAccreditationHistoryService,
        CheckAccreditationHistoryRepository checkAccreditationHistoryRepository,
        CheckAccreditationHistoryQueryService checkAccreditationHistoryQueryService
    ) {
        this.checkAccreditationHistoryService = checkAccreditationHistoryService;
        this.checkAccreditationHistoryRepository = checkAccreditationHistoryRepository;
        this.checkAccreditationHistoryQueryService = checkAccreditationHistoryQueryService;
    }

    /**
     * {@code POST  /check-accreditation-histories} : Create a new checkAccreditationHistory.
     *
     * @param checkAccreditationHistoryDTO the checkAccreditationHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkAccreditationHistoryDTO, or with status {@code 400 (Bad Request)} if the checkAccreditationHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-accreditation-histories")
    public ResponseEntity<CheckAccreditationHistoryDTO> createCheckAccreditationHistory(
        @RequestBody CheckAccreditationHistoryDTO checkAccreditationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CheckAccreditationHistory : {}", checkAccreditationHistoryDTO);
        if (checkAccreditationHistoryDTO.getCheckAccreditationHistoryId() != null) {
            throw new BadRequestAlertException("A new checkAccreditationHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckAccreditationHistoryDTO result = checkAccreditationHistoryService.save(checkAccreditationHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/check-accreditation-histories/" + result.getCheckAccreditationHistoryId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCheckAccreditationHistoryId().toString())
            )
            .body(result);
    }

    /**
     * {@code PUT  /check-accreditation-histories/:checkAccreditationHistoryId} : Updates an existing checkAccreditationHistory.
     *
     * @param checkAccreditationHistoryId the id of the checkAccreditationHistoryDTO to save.
     * @param checkAccreditationHistoryDTO the checkAccreditationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkAccreditationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the checkAccreditationHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkAccreditationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-accreditation-histories/{checkAccreditationHistoryId}")
    public ResponseEntity<CheckAccreditationHistoryDTO> updateCheckAccreditationHistory(
        @PathVariable(value = "checkAccreditationHistoryId", required = false) final Long checkAccreditationHistoryId,
        @RequestBody CheckAccreditationHistoryDTO checkAccreditationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CheckAccreditationHistory : {}, {}", checkAccreditationHistoryId, checkAccreditationHistoryDTO);
        if (checkAccreditationHistoryDTO.getCheckAccreditationHistoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(checkAccreditationHistoryId, checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkAccreditationHistoryRepository.existsById(checkAccreditationHistoryId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CheckAccreditationHistoryDTO result = checkAccreditationHistoryService.update(checkAccreditationHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    checkAccreditationHistoryDTO.getCheckAccreditationHistoryId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /check-accreditation-histories/:checkAccreditationHistoryId} : Partial updates given fields of an existing checkAccreditationHistory, field will ignore if it is null
     *
     * @param checkAccreditationHistoryId the id of the checkAccreditationHistoryDTO to save.
     * @param checkAccreditationHistoryDTO the checkAccreditationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkAccreditationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the checkAccreditationHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the checkAccreditationHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkAccreditationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/check-accreditation-histories/{checkAccreditationHistoryId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<CheckAccreditationHistoryDTO> partialUpdateCheckAccreditationHistory(
        @PathVariable(value = "checkAccreditationHistoryId", required = false) final Long checkAccreditationHistoryId,
        @RequestBody CheckAccreditationHistoryDTO checkAccreditationHistoryDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update CheckAccreditationHistory partially : {}, {}",
            checkAccreditationHistoryId,
            checkAccreditationHistoryDTO
        );
        if (checkAccreditationHistoryDTO.getCheckAccreditationHistoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(checkAccreditationHistoryId, checkAccreditationHistoryDTO.getCheckAccreditationHistoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkAccreditationHistoryRepository.existsById(checkAccreditationHistoryId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckAccreditationHistoryDTO> result = checkAccreditationHistoryService.partialUpdate(checkAccreditationHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                checkAccreditationHistoryDTO.getCheckAccreditationHistoryId().toString()
            )
        );
    }

    /**
     * {@code GET  /check-accreditation-histories} : get all the checkAccreditationHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkAccreditationHistories in body.
     */
    @GetMapping("/check-accreditation-histories")
    public ResponseEntity<List<CheckAccreditationHistoryDTO>> getAllCheckAccreditationHistories(
        CheckAccreditationHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CheckAccreditationHistories by criteria: {}", criteria);
        Page<CheckAccreditationHistoryDTO> page = checkAccreditationHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-accreditation-histories/count} : count all the checkAccreditationHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/check-accreditation-histories/count")
    public ResponseEntity<Long> countCheckAccreditationHistories(CheckAccreditationHistoryCriteria criteria) {
        log.debug("REST request to count CheckAccreditationHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(checkAccreditationHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /check-accreditation-histories/:id} : get the "id" checkAccreditationHistory.
     *
     * @param id the id of the checkAccreditationHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkAccreditationHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-accreditation-histories/{id}")
    public ResponseEntity<CheckAccreditationHistoryDTO> getCheckAccreditationHistory(@PathVariable Long id) {
        log.debug("REST request to get CheckAccreditationHistory : {}", id);
        Optional<CheckAccreditationHistoryDTO> checkAccreditationHistoryDTO = checkAccreditationHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkAccreditationHistoryDTO);
    }

    /**
     * {@code DELETE  /check-accreditation-histories/:id} : delete the "id" checkAccreditationHistory.
     *
     * @param id the id of the checkAccreditationHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-accreditation-histories/{id}")
    public ResponseEntity<Void> deleteCheckAccreditationHistory(@PathVariable Long id) {
        log.debug("REST request to delete CheckAccreditationHistory : {}", id);
        checkAccreditationHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
