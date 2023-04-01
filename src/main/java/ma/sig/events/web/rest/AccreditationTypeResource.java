package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AccreditationTypeRepository;
import ma.sig.events.service.AccreditationTypeQueryService;
import ma.sig.events.service.AccreditationTypeService;
import ma.sig.events.service.criteria.AccreditationTypeCriteria;
import ma.sig.events.service.dto.AccreditationTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.AccreditationType}.
 */
@RestController
@RequestMapping("/api")
public class AccreditationTypeResource {

    private final Logger log = LoggerFactory.getLogger(AccreditationTypeResource.class);

    private static final String ENTITY_NAME = "accreditationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccreditationTypeService accreditationTypeService;

    private final AccreditationTypeRepository accreditationTypeRepository;

    private final AccreditationTypeQueryService accreditationTypeQueryService;

    public AccreditationTypeResource(
        AccreditationTypeService accreditationTypeService,
        AccreditationTypeRepository accreditationTypeRepository,
        AccreditationTypeQueryService accreditationTypeQueryService
    ) {
        this.accreditationTypeService = accreditationTypeService;
        this.accreditationTypeRepository = accreditationTypeRepository;
        this.accreditationTypeQueryService = accreditationTypeQueryService;
    }

    /**
     * {@code POST  /accreditation-types} : Create a new accreditationType.
     *
     * @param accreditationTypeDTO the accreditationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accreditationTypeDTO, or with status {@code 400 (Bad Request)} if the accreditationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accreditation-types")
    public ResponseEntity<AccreditationTypeDTO> createAccreditationType(@Valid @RequestBody AccreditationTypeDTO accreditationTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccreditationType : {}", accreditationTypeDTO);
        if (accreditationTypeDTO.getAccreditationTypeId() != null) {
            throw new BadRequestAlertException("A new accreditationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccreditationTypeDTO result = accreditationTypeService.save(accreditationTypeDTO);
        return ResponseEntity
            .created(new URI("/api/accreditation-types/" + result.getAccreditationTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAccreditationTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accreditation-types/:accreditationTypeId} : Updates an existing accreditationType.
     *
     * @param accreditationTypeId the id of the accreditationTypeDTO to save.
     * @param accreditationTypeDTO the accreditationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accreditationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accreditation-types/{accreditationTypeId}")
    public ResponseEntity<AccreditationTypeDTO> updateAccreditationType(
        @PathVariable(value = "accreditationTypeId", required = false) final Long accreditationTypeId,
        @Valid @RequestBody AccreditationTypeDTO accreditationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccreditationType : {}, {}", accreditationTypeId, accreditationTypeDTO);
        if (accreditationTypeDTO.getAccreditationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accreditationTypeId, accreditationTypeDTO.getAccreditationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accreditationTypeRepository.existsById(accreditationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccreditationTypeDTO result = accreditationTypeService.update(accreditationTypeDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    accreditationTypeDTO.getAccreditationTypeId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /accreditation-types/:accreditationTypeId} : Partial updates given fields of an existing accreditationType, field will ignore if it is null
     *
     * @param accreditationTypeId the id of the accreditationTypeDTO to save.
     * @param accreditationTypeDTO the accreditationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accreditationTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accreditationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accreditation-types/{accreditationTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccreditationTypeDTO> partialUpdateAccreditationType(
        @PathVariable(value = "accreditationTypeId", required = false) final Long accreditationTypeId,
        @NotNull @RequestBody AccreditationTypeDTO accreditationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccreditationType partially : {}, {}", accreditationTypeId, accreditationTypeDTO);
        if (accreditationTypeDTO.getAccreditationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accreditationTypeId, accreditationTypeDTO.getAccreditationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accreditationTypeRepository.existsById(accreditationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccreditationTypeDTO> result = accreditationTypeService.partialUpdate(accreditationTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accreditationTypeDTO.getAccreditationTypeId().toString())
        );
    }

    /**
     * {@code GET  /accreditation-types} : get all the accreditationTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accreditationTypes in body.
     */
    @GetMapping("/accreditation-types")
    public ResponseEntity<List<AccreditationTypeDTO>> getAllAccreditationTypes(
        AccreditationTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AccreditationTypes by criteria: {}", criteria);
        Page<AccreditationTypeDTO> page = accreditationTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accreditation-types/count} : count all the accreditationTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accreditation-types/count")
    public ResponseEntity<Long> countAccreditationTypes(AccreditationTypeCriteria criteria) {
        log.debug("REST request to count AccreditationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(accreditationTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accreditation-types/:id} : get the "id" accreditationType.
     *
     * @param id the id of the accreditationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accreditationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accreditation-types/{id}")
    public ResponseEntity<AccreditationTypeDTO> getAccreditationType(@PathVariable Long id) {
        log.debug("REST request to get AccreditationType : {}", id);
        Optional<AccreditationTypeDTO> accreditationTypeDTO = accreditationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accreditationTypeDTO);
    }

    /**
     * {@code DELETE  /accreditation-types/:id} : delete the "id" accreditationType.
     *
     * @param id the id of the accreditationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accreditation-types/{id}")
    public ResponseEntity<Void> deleteAccreditationType(@PathVariable Long id) {
        log.debug("REST request to delete AccreditationType : {}", id);
        accreditationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
