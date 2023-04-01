package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.NationalityRepository;
import ma.sig.events.service.NationalityQueryService;
import ma.sig.events.service.NationalityService;
import ma.sig.events.service.criteria.NationalityCriteria;
import ma.sig.events.service.dto.NationalityDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Nationality}.
 */
@RestController
@RequestMapping("/api")
public class NationalityResource {

    private final Logger log = LoggerFactory.getLogger(NationalityResource.class);

    private static final String ENTITY_NAME = "nationality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationalityService nationalityService;

    private final NationalityRepository nationalityRepository;

    private final NationalityQueryService nationalityQueryService;

    public NationalityResource(
        NationalityService nationalityService,
        NationalityRepository nationalityRepository,
        NationalityQueryService nationalityQueryService
    ) {
        this.nationalityService = nationalityService;
        this.nationalityRepository = nationalityRepository;
        this.nationalityQueryService = nationalityQueryService;
    }

    /**
     * {@code POST  /nationalities} : Create a new nationality.
     *
     * @param nationalityDTO the nationalityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationalityDTO, or with status {@code 400 (Bad Request)} if the nationality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nationalities")
    public ResponseEntity<NationalityDTO> createNationality(@Valid @RequestBody NationalityDTO nationalityDTO) throws URISyntaxException {
        log.debug("REST request to save Nationality : {}", nationalityDTO);
        if (nationalityDTO.getNationalityId() != null) {
            throw new BadRequestAlertException("A new nationality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NationalityDTO result = nationalityService.save(nationalityDTO);
        return ResponseEntity
            .created(new URI("/api/nationalities/" + result.getNationalityId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNationalityId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nationalities/:nationalityId} : Updates an existing nationality.
     *
     * @param nationalityId the id of the nationalityDTO to save.
     * @param nationalityDTO the nationalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationalityDTO,
     * or with status {@code 400 (Bad Request)} if the nationalityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nationalities/{nationalityId}")
    public ResponseEntity<NationalityDTO> updateNationality(
        @PathVariable(value = "nationalityId", required = false) final Long nationalityId,
        @Valid @RequestBody NationalityDTO nationalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nationality : {}, {}", nationalityId, nationalityDTO);
        if (nationalityDTO.getNationalityId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nationalityId, nationalityDTO.getNationalityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationalityRepository.existsById(nationalityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NationalityDTO result = nationalityService.update(nationalityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nationalityDTO.getNationalityId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nationalities/:nationalityId} : Partial updates given fields of an existing nationality, field will ignore if it is null
     *
     * @param nationalityId the id of the nationalityDTO to save.
     * @param nationalityDTO the nationalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationalityDTO,
     * or with status {@code 400 (Bad Request)} if the nationalityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nationalityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nationalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nationalities/{nationalityId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NationalityDTO> partialUpdateNationality(
        @PathVariable(value = "nationalityId", required = false) final Long nationalityId,
        @NotNull @RequestBody NationalityDTO nationalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nationality partially : {}, {}", nationalityId, nationalityDTO);
        if (nationalityDTO.getNationalityId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(nationalityId, nationalityDTO.getNationalityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationalityRepository.existsById(nationalityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NationalityDTO> result = nationalityService.partialUpdate(nationalityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nationalityDTO.getNationalityId().toString())
        );
    }

    /**
     * {@code GET  /nationalities} : get all the nationalities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationalities in body.
     */
    @GetMapping("/nationalities")
    public ResponseEntity<List<NationalityDTO>> getAllNationalities(
        NationalityCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Nationalities by criteria: {}", criteria);
        Page<NationalityDTO> page = nationalityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nationalities/count} : count all the nationalities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nationalities/count")
    public ResponseEntity<Long> countNationalities(NationalityCriteria criteria) {
        log.debug("REST request to count Nationalities by criteria: {}", criteria);
        return ResponseEntity.ok().body(nationalityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nationalities/:id} : get the "id" nationality.
     *
     * @param id the id of the nationalityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationalityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nationalities/{id}")
    public ResponseEntity<NationalityDTO> getNationality(@PathVariable Long id) {
        log.debug("REST request to get Nationality : {}", id);
        Optional<NationalityDTO> nationalityDTO = nationalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nationalityDTO);
    }

    /**
     * {@code DELETE  /nationalities/:id} : delete the "id" nationality.
     *
     * @param id the id of the nationalityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nationalities/{id}")
    public ResponseEntity<Void> deleteNationality(@PathVariable Long id) {
        log.debug("REST request to delete Nationality : {}", id);
        nationalityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
