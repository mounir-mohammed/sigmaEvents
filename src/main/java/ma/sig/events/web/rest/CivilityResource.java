package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.CivilityRepository;
import ma.sig.events.service.CivilityQueryService;
import ma.sig.events.service.CivilityService;
import ma.sig.events.service.criteria.CivilityCriteria;
import ma.sig.events.service.dto.CivilityDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Civility}.
 */
@RestController
@RequestMapping("/api")
public class CivilityResource {

    private final Logger log = LoggerFactory.getLogger(CivilityResource.class);

    private static final String ENTITY_NAME = "civility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CivilityService civilityService;

    private final CivilityRepository civilityRepository;

    private final CivilityQueryService civilityQueryService;

    public CivilityResource(
        CivilityService civilityService,
        CivilityRepository civilityRepository,
        CivilityQueryService civilityQueryService
    ) {
        this.civilityService = civilityService;
        this.civilityRepository = civilityRepository;
        this.civilityQueryService = civilityQueryService;
    }

    /**
     * {@code POST  /civilities} : Create a new civility.
     *
     * @param civilityDTO the civilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new civilityDTO, or with status {@code 400 (Bad Request)} if the civility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/civilities")
    public ResponseEntity<CivilityDTO> createCivility(@Valid @RequestBody CivilityDTO civilityDTO) throws URISyntaxException {
        log.debug("REST request to save Civility : {}", civilityDTO);
        if (civilityDTO.getCivilityId() != null) {
            throw new BadRequestAlertException("A new civility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CivilityDTO result = civilityService.save(civilityDTO);
        return ResponseEntity
            .created(new URI("/api/civilities/" + result.getCivilityId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCivilityId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /civilities/:civilityId} : Updates an existing civility.
     *
     * @param civilityId the id of the civilityDTO to save.
     * @param civilityDTO the civilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civilityDTO,
     * or with status {@code 400 (Bad Request)} if the civilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the civilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/civilities/{civilityId}")
    public ResponseEntity<CivilityDTO> updateCivility(
        @PathVariable(value = "civilityId", required = false) final Long civilityId,
        @Valid @RequestBody CivilityDTO civilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Civility : {}, {}", civilityId, civilityDTO);
        if (civilityDTO.getCivilityId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(civilityId, civilityDTO.getCivilityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civilityRepository.existsById(civilityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CivilityDTO result = civilityService.update(civilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civilityDTO.getCivilityId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /civilities/:civilityId} : Partial updates given fields of an existing civility, field will ignore if it is null
     *
     * @param civilityId the id of the civilityDTO to save.
     * @param civilityDTO the civilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civilityDTO,
     * or with status {@code 400 (Bad Request)} if the civilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the civilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the civilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/civilities/{civilityId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CivilityDTO> partialUpdateCivility(
        @PathVariable(value = "civilityId", required = false) final Long civilityId,
        @NotNull @RequestBody CivilityDTO civilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Civility partially : {}, {}", civilityId, civilityDTO);
        if (civilityDTO.getCivilityId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(civilityId, civilityDTO.getCivilityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civilityRepository.existsById(civilityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CivilityDTO> result = civilityService.partialUpdate(civilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civilityDTO.getCivilityId().toString())
        );
    }

    /**
     * {@code GET  /civilities} : get all the civilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of civilities in body.
     */
    @GetMapping("/civilities")
    public ResponseEntity<List<CivilityDTO>> getAllCivilities(
        CivilityCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Civilities by criteria: {}", criteria);
        Page<CivilityDTO> page = civilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /civilities/count} : count all the civilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/civilities/count")
    public ResponseEntity<Long> countCivilities(CivilityCriteria criteria) {
        log.debug("REST request to count Civilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(civilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /civilities/:id} : get the "id" civility.
     *
     * @param id the id of the civilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the civilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/civilities/{id}")
    public ResponseEntity<CivilityDTO> getCivility(@PathVariable Long id) {
        log.debug("REST request to get Civility : {}", id);
        Optional<CivilityDTO> civilityDTO = civilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(civilityDTO);
    }

    /**
     * {@code DELETE  /civilities/:id} : delete the "id" civility.
     *
     * @param id the id of the civilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/civilities/{id}")
    public ResponseEntity<Void> deleteCivility(@PathVariable Long id) {
        log.debug("REST request to delete Civility : {}", id);
        civilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
