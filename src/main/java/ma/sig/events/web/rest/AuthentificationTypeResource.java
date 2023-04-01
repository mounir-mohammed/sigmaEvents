package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AuthentificationTypeRepository;
import ma.sig.events.service.AuthentificationTypeQueryService;
import ma.sig.events.service.AuthentificationTypeService;
import ma.sig.events.service.criteria.AuthentificationTypeCriteria;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.AuthentificationType}.
 */
@RestController
@RequestMapping("/api")
public class AuthentificationTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuthentificationTypeResource.class);

    private static final String ENTITY_NAME = "authentificationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthentificationTypeService authentificationTypeService;

    private final AuthentificationTypeRepository authentificationTypeRepository;

    private final AuthentificationTypeQueryService authentificationTypeQueryService;

    public AuthentificationTypeResource(
        AuthentificationTypeService authentificationTypeService,
        AuthentificationTypeRepository authentificationTypeRepository,
        AuthentificationTypeQueryService authentificationTypeQueryService
    ) {
        this.authentificationTypeService = authentificationTypeService;
        this.authentificationTypeRepository = authentificationTypeRepository;
        this.authentificationTypeQueryService = authentificationTypeQueryService;
    }

    /**
     * {@code POST  /authentification-types} : Create a new authentificationType.
     *
     * @param authentificationTypeDTO the authentificationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authentificationTypeDTO, or with status {@code 400 (Bad Request)} if the authentificationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authentification-types")
    public ResponseEntity<AuthentificationTypeDTO> createAuthentificationType(
        @Valid @RequestBody AuthentificationTypeDTO authentificationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AuthentificationType : {}", authentificationTypeDTO);
        if (authentificationTypeDTO.getAuthentificationTypeId() != null) {
            throw new BadRequestAlertException("A new authentificationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthentificationTypeDTO result = authentificationTypeService.save(authentificationTypeDTO);
        return ResponseEntity
            .created(new URI("/api/authentification-types/" + result.getAuthentificationTypeId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAuthentificationTypeId().toString())
            )
            .body(result);
    }

    /**
     * {@code PUT  /authentification-types/:authentificationTypeId} : Updates an existing authentificationType.
     *
     * @param authentificationTypeId the id of the authentificationTypeDTO to save.
     * @param authentificationTypeDTO the authentificationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authentificationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the authentificationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authentificationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/authentification-types/{authentificationTypeId}")
    public ResponseEntity<AuthentificationTypeDTO> updateAuthentificationType(
        @PathVariable(value = "authentificationTypeId", required = false) final Long authentificationTypeId,
        @Valid @RequestBody AuthentificationTypeDTO authentificationTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuthentificationType : {}, {}", authentificationTypeId, authentificationTypeDTO);
        if (authentificationTypeDTO.getAuthentificationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(authentificationTypeId, authentificationTypeDTO.getAuthentificationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authentificationTypeRepository.existsById(authentificationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthentificationTypeDTO result = authentificationTypeService.update(authentificationTypeDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    authentificationTypeDTO.getAuthentificationTypeId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /authentification-types/:authentificationTypeId} : Partial updates given fields of an existing authentificationType, field will ignore if it is null
     *
     * @param authentificationTypeId the id of the authentificationTypeDTO to save.
     * @param authentificationTypeDTO the authentificationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authentificationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the authentificationTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authentificationTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authentificationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/authentification-types/{authentificationTypeId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<AuthentificationTypeDTO> partialUpdateAuthentificationType(
        @PathVariable(value = "authentificationTypeId", required = false) final Long authentificationTypeId,
        @NotNull @RequestBody AuthentificationTypeDTO authentificationTypeDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update AuthentificationType partially : {}, {}",
            authentificationTypeId,
            authentificationTypeDTO
        );
        if (authentificationTypeDTO.getAuthentificationTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(authentificationTypeId, authentificationTypeDTO.getAuthentificationTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authentificationTypeRepository.existsById(authentificationTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthentificationTypeDTO> result = authentificationTypeService.partialUpdate(authentificationTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                authentificationTypeDTO.getAuthentificationTypeId().toString()
            )
        );
    }

    /**
     * {@code GET  /authentification-types} : get all the authentificationTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authentificationTypes in body.
     */
    @GetMapping("/authentification-types")
    public ResponseEntity<List<AuthentificationTypeDTO>> getAllAuthentificationTypes(
        AuthentificationTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AuthentificationTypes by criteria: {}", criteria);
        Page<AuthentificationTypeDTO> page = authentificationTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /authentification-types/count} : count all the authentificationTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/authentification-types/count")
    public ResponseEntity<Long> countAuthentificationTypes(AuthentificationTypeCriteria criteria) {
        log.debug("REST request to count AuthentificationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(authentificationTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /authentification-types/:id} : get the "id" authentificationType.
     *
     * @param id the id of the authentificationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authentificationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authentification-types/{id}")
    public ResponseEntity<AuthentificationTypeDTO> getAuthentificationType(@PathVariable Long id) {
        log.debug("REST request to get AuthentificationType : {}", id);
        Optional<AuthentificationTypeDTO> authentificationTypeDTO = authentificationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authentificationTypeDTO);
    }

    /**
     * {@code DELETE  /authentification-types/:id} : delete the "id" authentificationType.
     *
     * @param id the id of the authentificationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authentification-types/{id}")
    public ResponseEntity<Void> deleteAuthentificationType(@PathVariable Long id) {
        log.debug("REST request to delete AuthentificationType : {}", id);
        authentificationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
