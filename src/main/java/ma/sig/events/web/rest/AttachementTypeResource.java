package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AttachementTypeRepository;
import ma.sig.events.service.AttachementTypeQueryService;
import ma.sig.events.service.AttachementTypeService;
import ma.sig.events.service.criteria.AttachementTypeCriteria;
import ma.sig.events.service.dto.AttachementTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.AttachementType}.
 */
@RestController
@RequestMapping("/api")
public class AttachementTypeResource {

    private final Logger log = LoggerFactory.getLogger(AttachementTypeResource.class);

    private static final String ENTITY_NAME = "attachementType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachementTypeService attachementTypeService;

    private final AttachementTypeRepository attachementTypeRepository;

    private final AttachementTypeQueryService attachementTypeQueryService;

    public AttachementTypeResource(
        AttachementTypeService attachementTypeService,
        AttachementTypeRepository attachementTypeRepository,
        AttachementTypeQueryService attachementTypeQueryService
    ) {
        this.attachementTypeService = attachementTypeService;
        this.attachementTypeRepository = attachementTypeRepository;
        this.attachementTypeQueryService = attachementTypeQueryService;
    }

    /**
     * {@code POST  /attachement-types} : Create a new attachementType.
     *
     * @param attachementTypeDTO the attachementTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachementTypeDTO, or with status {@code 400 (Bad Request)} if the attachementType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachement-types")
    public ResponseEntity<AttachementTypeDTO> createAttachementType(@Valid @RequestBody AttachementTypeDTO attachementTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttachementType : {}", attachementTypeDTO);
        if (attachementTypeDTO.getAttachementTypeId() != null) {
            throw new BadRequestAlertException("A new attachementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachementTypeDTO result = attachementTypeService.save(attachementTypeDTO);
        return ResponseEntity
            .created(new URI("/api/attachement-types/" + result.getAttachementTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAttachementTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachement-types/:attachementTypeId} : Updates an existing attachementType.
     *
     * @param attachementTypeId the id of the attachementTypeDTO to save.
     * @param attachementTypeDTO the attachementTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachementTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachementTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachementTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachement-types/{attachementTypeId}")
    public ResponseEntity<AttachementTypeDTO> updateAttachementType(
        @PathVariable(value = "attachementTypeId", required = false) final Long attachementTypeId,
        @Valid @RequestBody AttachementTypeDTO attachementTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttachementType : {}, {}", attachementTypeId, attachementTypeDTO);
        if (attachementTypeDTO.getAttachementTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(attachementTypeId, attachementTypeDTO.getAttachementTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachementTypeRepository.existsById(attachementTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachementTypeDTO result = attachementTypeService.update(attachementTypeDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachementTypeDTO.getAttachementTypeId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /attachement-types/:attachementTypeId} : Partial updates given fields of an existing attachementType, field will ignore if it is null
     *
     * @param attachementTypeId the id of the attachementTypeDTO to save.
     * @param attachementTypeDTO the attachementTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachementTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attachementTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachementTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachementTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachement-types/{attachementTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachementTypeDTO> partialUpdateAttachementType(
        @PathVariable(value = "attachementTypeId", required = false) final Long attachementTypeId,
        @NotNull @RequestBody AttachementTypeDTO attachementTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttachementType partially : {}, {}", attachementTypeId, attachementTypeDTO);
        if (attachementTypeDTO.getAttachementTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(attachementTypeId, attachementTypeDTO.getAttachementTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachementTypeRepository.existsById(attachementTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachementTypeDTO> result = attachementTypeService.partialUpdate(attachementTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachementTypeDTO.getAttachementTypeId().toString())
        );
    }

    /**
     * {@code GET  /attachement-types} : get all the attachementTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachementTypes in body.
     */
    @GetMapping("/attachement-types")
    public ResponseEntity<List<AttachementTypeDTO>> getAllAttachementTypes(
        AttachementTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AttachementTypes by criteria: {}", criteria);
        Page<AttachementTypeDTO> page = attachementTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachement-types/count} : count all the attachementTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attachement-types/count")
    public ResponseEntity<Long> countAttachementTypes(AttachementTypeCriteria criteria) {
        log.debug("REST request to count AttachementTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(attachementTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attachement-types/:id} : get the "id" attachementType.
     *
     * @param id the id of the attachementTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachementTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachement-types/{id}")
    public ResponseEntity<AttachementTypeDTO> getAttachementType(@PathVariable Long id) {
        log.debug("REST request to get AttachementType : {}", id);
        Optional<AttachementTypeDTO> attachementTypeDTO = attachementTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachementTypeDTO);
    }

    /**
     * {@code DELETE  /attachement-types/:id} : delete the "id" attachementType.
     *
     * @param id the id of the attachementTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachement-types/{id}")
    public ResponseEntity<Void> deleteAttachementType(@PathVariable Long id) {
        log.debug("REST request to delete AttachementType : {}", id);
        attachementTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
