package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.OperationHistoryRepository;
import ma.sig.events.service.OperationHistoryQueryService;
import ma.sig.events.service.OperationHistoryService;
import ma.sig.events.service.criteria.OperationHistoryCriteria;
import ma.sig.events.service.dto.OperationHistoryDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.OperationHistory}.
 */
@RestController
@RequestMapping("/api")
public class OperationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(OperationHistoryResource.class);

    private static final String ENTITY_NAME = "operationHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationHistoryService operationHistoryService;

    private final OperationHistoryRepository operationHistoryRepository;

    private final OperationHistoryQueryService operationHistoryQueryService;

    public OperationHistoryResource(
        OperationHistoryService operationHistoryService,
        OperationHistoryRepository operationHistoryRepository,
        OperationHistoryQueryService operationHistoryQueryService
    ) {
        this.operationHistoryService = operationHistoryService;
        this.operationHistoryRepository = operationHistoryRepository;
        this.operationHistoryQueryService = operationHistoryQueryService;
    }

    /**
     * {@code POST  /operation-histories} : Create a new operationHistory.
     *
     * @param operationHistoryDTO the operationHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationHistoryDTO, or with status {@code 400 (Bad Request)} if the operationHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-histories")
    public ResponseEntity<OperationHistoryDTO> createOperationHistory(@Valid @RequestBody OperationHistoryDTO operationHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save OperationHistory : {}", operationHistoryDTO);
        if (operationHistoryDTO.getOperationHistoryId() != null) {
            throw new BadRequestAlertException("A new operationHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationHistoryDTO result = operationHistoryService.save(operationHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/operation-histories/" + result.getOperationHistoryId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOperationHistoryId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-histories/:operationHistoryId} : Updates an existing operationHistory.
     *
     * @param operationHistoryId the id of the operationHistoryDTO to save.
     * @param operationHistoryDTO the operationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the operationHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-histories/{operationHistoryId}")
    public ResponseEntity<OperationHistoryDTO> updateOperationHistory(
        @PathVariable(value = "operationHistoryId", required = false) final Long operationHistoryId,
        @Valid @RequestBody OperationHistoryDTO operationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OperationHistory : {}, {}", operationHistoryId, operationHistoryDTO);
        if (operationHistoryDTO.getOperationHistoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(operationHistoryId, operationHistoryDTO.getOperationHistoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationHistoryRepository.existsById(operationHistoryId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationHistoryDTO result = operationHistoryService.update(operationHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    operationHistoryDTO.getOperationHistoryId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /operation-histories/:operationHistoryId} : Partial updates given fields of an existing operationHistory, field will ignore if it is null
     *
     * @param operationHistoryId the id of the operationHistoryDTO to save.
     * @param operationHistoryDTO the operationHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the operationHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operationHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operation-histories/{operationHistoryId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperationHistoryDTO> partialUpdateOperationHistory(
        @PathVariable(value = "operationHistoryId", required = false) final Long operationHistoryId,
        @NotNull @RequestBody OperationHistoryDTO operationHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationHistory partially : {}, {}", operationHistoryId, operationHistoryDTO);
        if (operationHistoryDTO.getOperationHistoryId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(operationHistoryId, operationHistoryDTO.getOperationHistoryId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationHistoryRepository.existsById(operationHistoryId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationHistoryDTO> result = operationHistoryService.partialUpdate(operationHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationHistoryDTO.getOperationHistoryId().toString())
        );
    }

    /**
     * {@code GET  /operation-histories} : get all the operationHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationHistories in body.
     */
    @GetMapping("/operation-histories")
    public ResponseEntity<List<OperationHistoryDTO>> getAllOperationHistories(
        OperationHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OperationHistories by criteria: {}", criteria);
        Page<OperationHistoryDTO> page = operationHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operation-histories/count} : count all the operationHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/operation-histories/count")
    public ResponseEntity<Long> countOperationHistories(OperationHistoryCriteria criteria) {
        log.debug("REST request to count OperationHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(operationHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /operation-histories/:id} : get the "id" operationHistory.
     *
     * @param id the id of the operationHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-histories/{id}")
    public ResponseEntity<OperationHistoryDTO> getOperationHistory(@PathVariable Long id) {
        log.debug("REST request to get OperationHistory : {}", id);
        Optional<OperationHistoryDTO> operationHistoryDTO = operationHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationHistoryDTO);
    }

    /**
     * {@code DELETE  /operation-histories/:id} : delete the "id" operationHistory.
     *
     * @param id the id of the operationHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-histories/{id}")
    public ResponseEntity<Void> deleteOperationHistory(@PathVariable Long id) {
        log.debug("REST request to delete OperationHistory : {}", id);
        operationHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
