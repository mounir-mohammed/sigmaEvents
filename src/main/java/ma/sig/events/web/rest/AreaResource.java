package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AreaRepository;
import ma.sig.events.service.AreaQueryService;
import ma.sig.events.service.AreaService;
import ma.sig.events.service.criteria.AreaCriteria;
import ma.sig.events.service.dto.AreaDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Area}.
 */
@RestController
@RequestMapping("/api")
public class AreaResource {

    private final Logger log = LoggerFactory.getLogger(AreaResource.class);

    private static final String ENTITY_NAME = "area";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaService areaService;

    private final AreaRepository areaRepository;

    private final AreaQueryService areaQueryService;

    public AreaResource(AreaService areaService, AreaRepository areaRepository, AreaQueryService areaQueryService) {
        this.areaService = areaService;
        this.areaRepository = areaRepository;
        this.areaQueryService = areaQueryService;
    }

    /**
     * {@code POST  /areas} : Create a new area.
     *
     * @param areaDTO the areaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaDTO, or with status {@code 400 (Bad Request)} if the area has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/areas")
    public ResponseEntity<AreaDTO> createArea(@Valid @RequestBody AreaDTO areaDTO) throws URISyntaxException {
        log.debug("REST request to save Area : {}", areaDTO);
        if (areaDTO.getAreaId() != null) {
            throw new BadRequestAlertException("A new area cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaDTO result = areaService.save(areaDTO);
        return ResponseEntity
            .created(new URI("/api/areas/" + result.getAreaId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAreaId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /areas/:areaId} : Updates an existing area.
     *
     * @param areaId the id of the areaDTO to save.
     * @param areaDTO the areaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDTO,
     * or with status {@code 400 (Bad Request)} if the areaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/areas/{areaId}")
    public ResponseEntity<AreaDTO> updateArea(
        @PathVariable(value = "areaId", required = false) final Long areaId,
        @Valid @RequestBody AreaDTO areaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Area : {}, {}", areaId, areaDTO);
        if (areaDTO.getAreaId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(areaId, areaDTO.getAreaId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaRepository.existsById(areaId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AreaDTO result = areaService.update(areaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDTO.getAreaId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /areas/:areaId} : Partial updates given fields of an existing area, field will ignore if it is null
     *
     * @param areaId the id of the areaDTO to save.
     * @param areaDTO the areaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDTO,
     * or with status {@code 400 (Bad Request)} if the areaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/areas/{areaId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaDTO> partialUpdateArea(
        @PathVariable(value = "areaId", required = false) final Long areaId,
        @NotNull @RequestBody AreaDTO areaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Area partially : {}, {}", areaId, areaDTO);
        if (areaDTO.getAreaId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(areaId, areaDTO.getAreaId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaRepository.existsById(areaId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaDTO> result = areaService.partialUpdate(areaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDTO.getAreaId().toString())
        );
    }

    /**
     * {@code GET  /areas} : get all the areas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areas in body.
     */
    @GetMapping("/areas")
    public ResponseEntity<List<AreaDTO>> getAllAreas(
        AreaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Areas by criteria: {}", criteria);
        Page<AreaDTO> page = areaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /areas/count} : count all the areas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/areas/count")
    public ResponseEntity<Long> countAreas(AreaCriteria criteria) {
        log.debug("REST request to count Areas by criteria: {}", criteria);
        return ResponseEntity.ok().body(areaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /areas/:id} : get the "id" area.
     *
     * @param id the id of the areaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/areas/{id}")
    public ResponseEntity<AreaDTO> getArea(@PathVariable Long id) {
        log.debug("REST request to get Area : {}", id);
        Optional<AreaDTO> areaDTO = areaQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(areaDTO);
    }

    /**
     * {@code DELETE  /areas/:id} : delete the "id" area.
     *
     * @param id the id of the areaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/areas/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        log.debug("REST request to delete Area : {}", id);
        areaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
