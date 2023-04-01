package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.OperationTypeRepository;
import ma.sig.events.service.OperationTypeQueryService;
import ma.sig.events.service.OperationTypeService;
import ma.sig.events.service.criteria.OperationTypeCriteria;
import ma.sig.events.service.dto.OperationTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.OperationType}.
 */
@RestController
@RequestMapping("/api")
public class OperationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OperationTypeResource.class);

    private static final String ENTITY_NAME = "operationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationTypeService operationTypeService;

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeQueryService operationTypeQueryService;

    public OperationTypeResource(
        OperationTypeService operationTypeService,
        OperationTypeRepository operationTypeRepository,
        OperationTypeQueryService operationTypeQueryService
    ) {
        this.operationTypeService = operationTypeService;
        this.operationTypeRepository = operationTypeRepository;
        this.operationTypeQueryService = operationTypeQueryService;
    }

    /**
     * {@code POST  /operation-types} : Create a new operationType.
     *
     * @param operationTypeDTO the operationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationTypeDTO, or with status {@code 400 (Bad Request)} if the operationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-types")
    public ResponseEntity<OperationTypeDTO> createOperationType(@Valid @RequestBody OperationTypeDTO operationTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save OperationType : {}", operationTypeDTO);
        if (operationTypeDTO.getOperationTypeId() != null) {
            throw new BadRequestAlertException("A new operationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationTypeDTO result = operationTypeService.save(operationTypeDTO);
        return ResponseEntity
            .created(new URI("/api/operation-types/" + result.getOperationTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOperationTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-types/:operationTypeId} : Updates an existing operationType.
     *
     * @param operationTypeId the id of the operationTypeDTO to save.
     * @param operationTypeDTO the operationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the operationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-types/{operationTypeId}")
    public ResponseEntity<OperationTypeDTO> updateOperationType(
        @PathVariable(value = "operationTypeId", required = false) final Long operationTypeId,
        @Valid @RequestBody OperationTypeDTO operationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OperationType : {}, {}", operationTypeId, operationTypeDTO);
        if (operationTypeDTO.getOperationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(operationTypeId, operationTypeDTO.getOperationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationTypeRepository.existsById(operationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationTypeDTO result = operationTypeService.update(operationTypeDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationTypeDTO.getOperationTypeId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /operation-types/:operationTypeId} : Partial updates given fields of an existing operationType, field will ignore if it is null
     *
     * @param operationTypeId the id of the operationTypeDTO to save.
     * @param operationTypeDTO the operationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the operationTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operationTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operation-types/{operationTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperationTypeDTO> partialUpdateOperationType(
        @PathVariable(value = "operationTypeId", required = false) final Long operationTypeId,
        @NotNull @RequestBody OperationTypeDTO operationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationType partially : {}, {}", operationTypeId, operationTypeDTO);
        if (operationTypeDTO.getOperationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(operationTypeId, operationTypeDTO.getOperationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationTypeRepository.existsById(operationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationTypeDTO> result = operationTypeService.partialUpdate(operationTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationTypeDTO.getOperationTypeId().toString())
        );
    }

    /**
     * {@code GET  /operation-types} : get all the operationTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationTypes in body.
     */
    @GetMapping("/operation-types")
    public ResponseEntity<List<OperationTypeDTO>> getAllOperationTypes(
        OperationTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OperationTypes by criteria: {}", criteria);
        Page<OperationTypeDTO> page = operationTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operation-types/count} : count all the operationTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/operation-types/count")
    public ResponseEntity<Long> countOperationTypes(OperationTypeCriteria criteria) {
        log.debug("REST request to count OperationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(operationTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /operation-types/:id} : get the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-types/{id}")
    public ResponseEntity<OperationTypeDTO> getOperationType(@PathVariable Long id) {
        log.debug("REST request to get OperationType : {}", id);
        Optional<OperationTypeDTO> operationTypeDTO = operationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationTypeDTO);
    }

    /**
     * {@code DELETE  /operation-types/:id} : delete the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-types/{id}")
    public ResponseEntity<Void> deleteOperationType(@PathVariable Long id) {
        log.debug("REST request to delete OperationType : {}", id);
        operationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
