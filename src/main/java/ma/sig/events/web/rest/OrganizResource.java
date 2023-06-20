package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.OrganizRepository;
import ma.sig.events.service.OrganizQueryService;
import ma.sig.events.service.OrganizService;
import ma.sig.events.service.criteria.OrganizCriteria;
import ma.sig.events.service.dto.OrganizDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Organiz}.
 */
@RestController
@RequestMapping("/api")
public class OrganizResource {

    private final Logger log = LoggerFactory.getLogger(OrganizResource.class);

    private static final String ENTITY_NAME = "organiz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizService organizService;

    private final OrganizRepository organizRepository;

    private final OrganizQueryService organizQueryService;

    public OrganizResource(OrganizService organizService, OrganizRepository organizRepository, OrganizQueryService organizQueryService) {
        this.organizService = organizService;
        this.organizRepository = organizRepository;
        this.organizQueryService = organizQueryService;
    }

    /**
     * {@code POST  /organizs} : Create a new organiz.
     *
     * @param organizDTO the organizDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizDTO, or with status {@code 400 (Bad Request)} if the organiz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organizs")
    public ResponseEntity<OrganizDTO> createOrganiz(@Valid @RequestBody OrganizDTO organizDTO) throws URISyntaxException {
        log.debug("REST request to save Organiz : {}", organizDTO);
        if (organizDTO.getOrganizId() != null) {
            throw new BadRequestAlertException("A new organiz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizDTO result = organizService.save(organizDTO);
        return ResponseEntity
            .created(new URI("/api/organizs/" + result.getOrganizId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOrganizId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organizs/:organizId} : Updates an existing organiz.
     *
     * @param organizId the id of the organizDTO to save.
     * @param organizDTO the organizDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizDTO,
     * or with status {@code 400 (Bad Request)} if the organizDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organizs/{organizId}")
    public ResponseEntity<OrganizDTO> updateOrganiz(
        @PathVariable(value = "organizId", required = false) final Long organizId,
        @Valid @RequestBody OrganizDTO organizDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Organiz : {}, {}", organizId, organizDTO);
        if (organizDTO.getOrganizId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(organizId, organizDTO.getOrganizId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizRepository.existsById(organizId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganizDTO result = organizService.update(organizDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizDTO.getOrganizId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organizs/:organizId} : Partial updates given fields of an existing organiz, field will ignore if it is null
     *
     * @param organizId the id of the organizDTO to save.
     * @param organizDTO the organizDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizDTO,
     * or with status {@code 400 (Bad Request)} if the organizDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organizs/{organizId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizDTO> partialUpdateOrganiz(
        @PathVariable(value = "organizId", required = false) final Long organizId,
        @NotNull @RequestBody OrganizDTO organizDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organiz partially : {}, {}", organizId, organizDTO);
        if (organizDTO.getOrganizId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(organizId, organizDTO.getOrganizId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizRepository.existsById(organizId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizDTO> result = organizService.partialUpdate(organizDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizDTO.getOrganizId().toString())
        );
    }

    /**
     * {@code GET  /organizs} : get all the organizs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizs in body.
     */
    @GetMapping("/organizs")
    public ResponseEntity<List<OrganizDTO>> getAllOrganizs(
        OrganizCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Organizs by criteria: {}", criteria);
        Page<OrganizDTO> page = organizQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organizs/count} : count all the organizs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/organizs/count")
    public ResponseEntity<Long> countOrganizs(OrganizCriteria criteria) {
        log.debug("REST request to count Organizs by criteria: {}", criteria);
        return ResponseEntity.ok().body(organizQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /organizs/:id} : get the "id" organiz.
     *
     * @param id the id of the organizDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organizs/{id}")
    public ResponseEntity<OrganizDTO> getOrganiz(@PathVariable Long id) {
        log.debug("REST request to get Organiz : {}", id);
        Optional<OrganizDTO> organizDTO = organizQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(organizDTO);
    }

    /**
     * {@code DELETE  /organizs/:id} : delete the "id" organiz.
     *
     * @param id the id of the organizDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organizs/{id}")
    public ResponseEntity<Void> deleteOrganiz(@PathVariable Long id) {
        log.debug("REST request to delete Organiz : {}", id);
        organizService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
