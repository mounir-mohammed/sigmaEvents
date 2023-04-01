package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.sig.events.repository.LogHistoryRepository;
import ma.sig.events.service.LogHistoryQueryService;
import ma.sig.events.service.LogHistoryService;
import ma.sig.events.service.criteria.LogHistoryCriteria;
import ma.sig.events.service.dto.LogHistoryDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.LogHistory}.
 */
@RestController
@RequestMapping("/api")
public class LogHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LogHistoryResource.class);

    private static final String ENTITY_NAME = "logHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogHistoryService logHistoryService;

    private final LogHistoryRepository logHistoryRepository;

    private final LogHistoryQueryService logHistoryQueryService;

    public LogHistoryResource(
        LogHistoryService logHistoryService,
        LogHistoryRepository logHistoryRepository,
        LogHistoryQueryService logHistoryQueryService
    ) {
        this.logHistoryService = logHistoryService;
        this.logHistoryRepository = logHistoryRepository;
        this.logHistoryQueryService = logHistoryQueryService;
    }

    /**
     * {@code POST  /log-histories} : Create a new logHistory.
     *
     * @param logHistoryDTO the logHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logHistoryDTO, or with status {@code 400 (Bad Request)} if the logHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-histories")
    public ResponseEntity<LogHistoryDTO> createLogHistory(@RequestBody LogHistoryDTO logHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save LogHistory : {}", logHistoryDTO);
        if (logHistoryDTO.getLogHistory() != null) {
            throw new BadRequestAlertException("A new logHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogHistoryDTO result = logHistoryService.save(logHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/log-histories/" + result.getLogHistory()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getLogHistory().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-histories/:logHistory} : Updates an existing logHistory.
     *
     * @param logHistory the id of the logHistoryDTO to save.
     * @param logHistoryDTO the logHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the logHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-histories/{logHistory}")
    public ResponseEntity<LogHistoryDTO> updateLogHistory(
        @PathVariable(value = "logHistory", required = false) final Long logHistory,
        @RequestBody LogHistoryDTO logHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LogHistory : {}, {}", logHistory, logHistoryDTO);
        if (logHistoryDTO.getLogHistory() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(logHistory, logHistoryDTO.getLogHistory())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logHistoryRepository.existsById(logHistory)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LogHistoryDTO result = logHistoryService.update(logHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logHistoryDTO.getLogHistory().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /log-histories/:logHistory} : Partial updates given fields of an existing logHistory, field will ignore if it is null
     *
     * @param logHistory the id of the logHistoryDTO to save.
     * @param logHistoryDTO the logHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the logHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the logHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the logHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/log-histories/{logHistory}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LogHistoryDTO> partialUpdateLogHistory(
        @PathVariable(value = "logHistory", required = false) final Long logHistory,
        @RequestBody LogHistoryDTO logHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LogHistory partially : {}, {}", logHistory, logHistoryDTO);
        if (logHistoryDTO.getLogHistory() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(logHistory, logHistoryDTO.getLogHistory())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!logHistoryRepository.existsById(logHistory)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LogHistoryDTO> result = logHistoryService.partialUpdate(logHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logHistoryDTO.getLogHistory().toString())
        );
    }

    /**
     * {@code GET  /log-histories} : get all the logHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logHistories in body.
     */
    @GetMapping("/log-histories")
    public ResponseEntity<List<LogHistoryDTO>> getAllLogHistories(
        LogHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LogHistories by criteria: {}", criteria);
        Page<LogHistoryDTO> page = logHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-histories/count} : count all the logHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/log-histories/count")
    public ResponseEntity<Long> countLogHistories(LogHistoryCriteria criteria) {
        log.debug("REST request to count LogHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(logHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /log-histories/:id} : get the "id" logHistory.
     *
     * @param id the id of the logHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-histories/{id}")
    public ResponseEntity<LogHistoryDTO> getLogHistory(@PathVariable Long id) {
        log.debug("REST request to get LogHistory : {}", id);
        Optional<LogHistoryDTO> logHistoryDTO = logHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logHistoryDTO);
    }

    /**
     * {@code DELETE  /log-histories/:id} : delete the "id" logHistory.
     *
     * @param id the id of the logHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-histories/{id}")
    public ResponseEntity<Void> deleteLogHistory(@PathVariable Long id) {
        log.debug("REST request to delete LogHistory : {}", id);
        logHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
