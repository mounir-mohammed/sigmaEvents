package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AttachementRepository;
import ma.sig.events.service.AttachementQueryService;
import ma.sig.events.service.AttachementService;
import ma.sig.events.service.criteria.AttachementCriteria;
import ma.sig.events.service.dto.AttachementDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Attachement}.
 */
@RestController
@RequestMapping("/api")
public class AttachementResource {

    private final Logger log = LoggerFactory.getLogger(AttachementResource.class);

    private static final String ENTITY_NAME = "attachement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachementService attachementService;

    private final AttachementRepository attachementRepository;

    private final AttachementQueryService attachementQueryService;

    public AttachementResource(
        AttachementService attachementService,
        AttachementRepository attachementRepository,
        AttachementQueryService attachementQueryService
    ) {
        this.attachementService = attachementService;
        this.attachementRepository = attachementRepository;
        this.attachementQueryService = attachementQueryService;
    }

    /**
     * {@code POST  /attachements} : Create a new attachement.
     *
     * @param attachementDTO the attachementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachementDTO, or with status {@code 400 (Bad Request)} if the attachement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attachements")
    public ResponseEntity<AttachementDTO> createAttachement(@Valid @RequestBody AttachementDTO attachementDTO) throws URISyntaxException {
        log.debug("REST request to save Attachement : {}", attachementDTO);
        if (attachementDTO.getAttachementId() != null) {
            throw new BadRequestAlertException("A new attachement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachementDTO result = attachementService.save(attachementDTO);
        return ResponseEntity
            .created(new URI("/api/attachements/" + result.getAttachementId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAttachementId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachements/:attachementId} : Updates an existing attachement.
     *
     * @param attachementId the id of the attachementDTO to save.
     * @param attachementDTO the attachementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachementDTO,
     * or with status {@code 400 (Bad Request)} if the attachementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attachements/{attachementId}")
    public ResponseEntity<AttachementDTO> updateAttachement(
        @PathVariable(value = "attachementId", required = false) final Long attachementId,
        @Valid @RequestBody AttachementDTO attachementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Attachement : {}, {}", attachementId, attachementDTO);
        if (attachementDTO.getAttachementId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(attachementId, attachementDTO.getAttachementId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachementRepository.existsById(attachementId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachementDTO result = attachementService.update(attachementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachementDTO.getAttachementId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachements/:attachementId} : Partial updates given fields of an existing attachement, field will ignore if it is null
     *
     * @param attachementId the id of the attachementDTO to save.
     * @param attachementDTO the attachementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachementDTO,
     * or with status {@code 400 (Bad Request)} if the attachementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attachements/{attachementId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachementDTO> partialUpdateAttachement(
        @PathVariable(value = "attachementId", required = false) final Long attachementId,
        @NotNull @RequestBody AttachementDTO attachementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Attachement partially : {}, {}", attachementId, attachementDTO);
        if (attachementDTO.getAttachementId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(attachementId, attachementDTO.getAttachementId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachementRepository.existsById(attachementId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachementDTO> result = attachementService.partialUpdate(attachementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attachementDTO.getAttachementId().toString())
        );
    }

    /**
     * {@code GET  /attachements} : get all the attachements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachements in body.
     */
    @GetMapping("/attachements")
    public ResponseEntity<List<AttachementDTO>> getAllAttachements(
        AttachementCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Attachements by criteria: {}", criteria);
        Page<AttachementDTO> page = attachementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachements/count} : count all the attachements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attachements/count")
    public ResponseEntity<Long> countAttachements(AttachementCriteria criteria) {
        log.debug("REST request to count Attachements by criteria: {}", criteria);
        return ResponseEntity.ok().body(attachementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attachements/:id} : get the "id" attachement.
     *
     * @param id the id of the attachementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attachements/{id}")
    public ResponseEntity<AttachementDTO> getAttachement(@PathVariable Long id) {
        log.debug("REST request to get Attachement : {}", id);
        Optional<AttachementDTO> attachementDTO = attachementQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(attachementDTO);
    }

    /**
     * {@code DELETE  /attachements/:id} : delete the "id" attachement.
     *
     * @param id the id of the attachementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attachements/{id}")
    public ResponseEntity<Void> deleteAttachement(@PathVariable Long id) {
        log.debug("REST request to delete Attachement : {}", id);
        attachementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
