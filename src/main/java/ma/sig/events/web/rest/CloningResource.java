package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.domain.User;
import ma.sig.events.repository.CloningRepository;
import ma.sig.events.security.SecurityUtils;
import ma.sig.events.service.CloningQueryService;
import ma.sig.events.service.CloningService;
import ma.sig.events.service.JobService;
import ma.sig.events.service.criteria.CloningCriteria;
import ma.sig.events.service.dto.CloningDTO;
import ma.sig.events.service.dto.Job;
import ma.sig.events.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.sig.events.domain.Cloning}.
 */
@RestController
@RequestMapping("/api")
public class CloningResource {

    private final Logger log = LoggerFactory.getLogger(CloningResource.class);

    private static final String ENTITY_NAME = "cloning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CloningService cloningService;

    private final CloningRepository cloningRepository;

    private final CloningQueryService cloningQueryService;

    private final JobService jobService;

    public CloningResource(
        CloningService cloningService,
        CloningRepository cloningRepository,
        CloningQueryService cloningQueryService,
        JobService jobService
    ) {
        this.cloningService = cloningService;
        this.cloningRepository = cloningRepository;
        this.cloningQueryService = cloningQueryService;
        this.jobService = jobService;
    }

    /**
     * {@code POST  /clonings} : Create a new cloning.
     *
     * @param cloningDTO the cloningDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloningDTO, or with status {@code 400 (Bad Request)} if the cloning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/clonings")
    public ResponseEntity<Map<String, String>> createCloning(@Valid @RequestBody CloningDTO cloningDTO) {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();

        String jobId = jobService.submit(() -> {
            // your long-running cloning logic here
            log.debug("REST request to save Cloning : {}", cloningDTO);
            if (cloningDTO.getCloningId() != null) {
                throw new BadRequestAlertException("A new cloning cannot already have an ID", ENTITY_NAME, "idexists");
            }
            cloningService.cloneAsync(cloningDTO, currentUserLogin.orElse(null));
        });
        return ResponseEntity.accepted().body(Map.of("jobId", jobId));
    }

    /**
     * {@code PUT  /clonings/:cloningId} : Updates an existing cloning.
     *
     * @param cloningId the id of the cloningDTO to save.
     * @param cloningDTO the cloningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloningDTO,
     * or with status {@code 400 (Bad Request)} if the cloningDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clonings/{cloningId}")
    public ResponseEntity<CloningDTO> updateCloning(
        @PathVariable(value = "cloningId", required = false) final Long cloningId,
        @Valid @RequestBody CloningDTO cloningDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cloning : {}, {}", cloningId, cloningDTO);
        if (cloningDTO.getCloningId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(cloningId, cloningDTO.getCloningId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cloningRepository.existsById(cloningId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CloningDTO result = cloningService.update(cloningDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cloningDTO.getCloningId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clonings/:cloningId} : Partial updates given fields of an existing cloning, field will ignore if it is null
     *
     * @param cloningId the id of the cloningDTO to save.
     * @param cloningDTO the cloningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloningDTO,
     * or with status {@code 400 (Bad Request)} if the cloningDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cloningDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cloningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clonings/{cloningId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CloningDTO> partialUpdateCloning(
        @PathVariable(value = "cloningId", required = false) final Long cloningId,
        @NotNull @RequestBody CloningDTO cloningDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cloning partially : {}, {}", cloningId, cloningDTO);
        if (cloningDTO.getCloningId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(cloningId, cloningDTO.getCloningId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cloningRepository.existsById(cloningId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CloningDTO> result = cloningService.partialUpdate(cloningDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cloningDTO.getCloningId().toString())
        );
    }

    /**
     * {@code GET  /clonings} : get all the clonings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clonings in body.
     */
    @GetMapping("/clonings")
    public ResponseEntity<List<CloningDTO>> getAllClonings(
        CloningCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Clonings by criteria: {}", criteria);
        Page<CloningDTO> page = cloningQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clonings/count} : count all the clonings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/clonings/count")
    public ResponseEntity<Long> countClonings(CloningCriteria criteria) {
        log.debug("REST request to count Clonings by criteria: {}", criteria);
        return ResponseEntity.ok().body(cloningQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /clonings/:id} : get the "id" cloning.
     *
     * @param id the id of the cloningDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloningDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clonings/{id}")
    public ResponseEntity<CloningDTO> getCloning(@PathVariable Long id) {
        log.debug("REST request to get Cloning : {}", id);
        Optional<CloningDTO> cloningDTO = cloningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cloningDTO);
    }

    /**
     * {@code DELETE  /clonings/:id} : delete the "id" cloning.
     *
     * @param id the id of the cloningDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clonings/{id}")
    public ResponseEntity<Void> deleteCloning(@PathVariable Long id) {
        log.debug("REST request to delete Cloning : {}", id);
        cloningService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/clonings/job/{jobId}/status")
    public ResponseEntity<Map<String, String>> getJobStatus(@PathVariable String jobId) {
        Job job = jobService.getJob(jobId);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("status", job.getStatus().name()));
    }
}
