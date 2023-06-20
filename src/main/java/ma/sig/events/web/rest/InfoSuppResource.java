package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.InfoSuppRepository;
import ma.sig.events.service.InfoSuppQueryService;
import ma.sig.events.service.InfoSuppService;
import ma.sig.events.service.criteria.InfoSuppCriteria;
import ma.sig.events.service.dto.InfoSuppDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.InfoSupp}.
 */
@RestController
@RequestMapping("/api")
public class InfoSuppResource {

    private final Logger log = LoggerFactory.getLogger(InfoSuppResource.class);

    private static final String ENTITY_NAME = "infoSupp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoSuppService infoSuppService;

    private final InfoSuppRepository infoSuppRepository;

    private final InfoSuppQueryService infoSuppQueryService;

    public InfoSuppResource(
        InfoSuppService infoSuppService,
        InfoSuppRepository infoSuppRepository,
        InfoSuppQueryService infoSuppQueryService
    ) {
        this.infoSuppService = infoSuppService;
        this.infoSuppRepository = infoSuppRepository;
        this.infoSuppQueryService = infoSuppQueryService;
    }

    /**
     * {@code POST  /info-supps} : Create a new infoSupp.
     *
     * @param infoSuppDTO the infoSuppDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoSuppDTO, or with status {@code 400 (Bad Request)} if the infoSupp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-supps")
    public ResponseEntity<InfoSuppDTO> createInfoSupp(@Valid @RequestBody InfoSuppDTO infoSuppDTO) throws URISyntaxException {
        log.debug("REST request to save InfoSupp : {}", infoSuppDTO);
        if (infoSuppDTO.getInfoSuppId() != null) {
            throw new BadRequestAlertException("A new infoSupp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoSuppDTO result = infoSuppService.save(infoSuppDTO);
        return ResponseEntity
            .created(new URI("/api/info-supps/" + result.getInfoSuppId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getInfoSuppId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-supps/:infoSuppId} : Updates an existing infoSupp.
     *
     * @param infoSuppId the id of the infoSuppDTO to save.
     * @param infoSuppDTO the infoSuppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoSuppDTO,
     * or with status {@code 400 (Bad Request)} if the infoSuppDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoSuppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-supps/{infoSuppId}")
    public ResponseEntity<InfoSuppDTO> updateInfoSupp(
        @PathVariable(value = "infoSuppId", required = false) final Long infoSuppId,
        @Valid @RequestBody InfoSuppDTO infoSuppDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InfoSupp : {}, {}", infoSuppId, infoSuppDTO);
        if (infoSuppDTO.getInfoSuppId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(infoSuppId, infoSuppDTO.getInfoSuppId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoSuppRepository.existsById(infoSuppId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InfoSuppDTO result = infoSuppService.update(infoSuppDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoSuppDTO.getInfoSuppId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /info-supps/:infoSuppId} : Partial updates given fields of an existing infoSupp, field will ignore if it is null
     *
     * @param infoSuppId the id of the infoSuppDTO to save.
     * @param infoSuppDTO the infoSuppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoSuppDTO,
     * or with status {@code 400 (Bad Request)} if the infoSuppDTO is not valid,
     * or with status {@code 404 (Not Found)} if the infoSuppDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoSuppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-supps/{infoSuppId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InfoSuppDTO> partialUpdateInfoSupp(
        @PathVariable(value = "infoSuppId", required = false) final Long infoSuppId,
        @NotNull @RequestBody InfoSuppDTO infoSuppDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoSupp partially : {}, {}", infoSuppId, infoSuppDTO);
        if (infoSuppDTO.getInfoSuppId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(infoSuppId, infoSuppDTO.getInfoSuppId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoSuppRepository.existsById(infoSuppId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InfoSuppDTO> result = infoSuppService.partialUpdate(infoSuppDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoSuppDTO.getInfoSuppId().toString())
        );
    }

    /**
     * {@code GET  /info-supps} : get all the infoSupps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoSupps in body.
     */
    @GetMapping("/info-supps")
    public ResponseEntity<List<InfoSuppDTO>> getAllInfoSupps(
        InfoSuppCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InfoSupps by criteria: {}", criteria);
        Page<InfoSuppDTO> page = infoSuppQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /info-supps/count} : count all the infoSupps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/info-supps/count")
    public ResponseEntity<Long> countInfoSupps(InfoSuppCriteria criteria) {
        log.debug("REST request to count InfoSupps by criteria: {}", criteria);
        return ResponseEntity.ok().body(infoSuppQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /info-supps/:id} : get the "id" infoSupp.
     *
     * @param id the id of the infoSuppDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoSuppDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-supps/{id}")
    public ResponseEntity<InfoSuppDTO> getInfoSupp(@PathVariable Long id) {
        log.debug("REST request to get InfoSupp : {}", id);
        Optional<InfoSuppDTO> infoSuppDTO = infoSuppQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(infoSuppDTO);
    }

    /**
     * {@code DELETE  /info-supps/:id} : delete the "id" infoSupp.
     *
     * @param id the id of the infoSuppDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-supps/{id}")
    public ResponseEntity<Void> deleteInfoSupp(@PathVariable Long id) {
        log.debug("REST request to delete InfoSupp : {}", id);
        infoSuppService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
