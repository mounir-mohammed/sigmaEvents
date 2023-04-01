package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.InfoSuppTypeRepository;
import ma.sig.events.service.InfoSuppTypeQueryService;
import ma.sig.events.service.InfoSuppTypeService;
import ma.sig.events.service.criteria.InfoSuppTypeCriteria;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.InfoSuppType}.
 */
@RestController
@RequestMapping("/api")
public class InfoSuppTypeResource {

    private final Logger log = LoggerFactory.getLogger(InfoSuppTypeResource.class);

    private static final String ENTITY_NAME = "infoSuppType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoSuppTypeService infoSuppTypeService;

    private final InfoSuppTypeRepository infoSuppTypeRepository;

    private final InfoSuppTypeQueryService infoSuppTypeQueryService;

    public InfoSuppTypeResource(
        InfoSuppTypeService infoSuppTypeService,
        InfoSuppTypeRepository infoSuppTypeRepository,
        InfoSuppTypeQueryService infoSuppTypeQueryService
    ) {
        this.infoSuppTypeService = infoSuppTypeService;
        this.infoSuppTypeRepository = infoSuppTypeRepository;
        this.infoSuppTypeQueryService = infoSuppTypeQueryService;
    }

    /**
     * {@code POST  /info-supp-types} : Create a new infoSuppType.
     *
     * @param infoSuppTypeDTO the infoSuppTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoSuppTypeDTO, or with status {@code 400 (Bad Request)} if the infoSuppType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-supp-types")
    public ResponseEntity<InfoSuppTypeDTO> createInfoSuppType(@Valid @RequestBody InfoSuppTypeDTO infoSuppTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save InfoSuppType : {}", infoSuppTypeDTO);
        if (infoSuppTypeDTO.getInfoSuppTypeId() != null) {
            throw new BadRequestAlertException("A new infoSuppType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoSuppTypeDTO result = infoSuppTypeService.save(infoSuppTypeDTO);
        return ResponseEntity
            .created(new URI("/api/info-supp-types/" + result.getInfoSuppTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getInfoSuppTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-supp-types/:infoSuppTypeId} : Updates an existing infoSuppType.
     *
     * @param infoSuppTypeId the id of the infoSuppTypeDTO to save.
     * @param infoSuppTypeDTO the infoSuppTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoSuppTypeDTO,
     * or with status {@code 400 (Bad Request)} if the infoSuppTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoSuppTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-supp-types/{infoSuppTypeId}")
    public ResponseEntity<InfoSuppTypeDTO> updateInfoSuppType(
        @PathVariable(value = "infoSuppTypeId", required = false) final Long infoSuppTypeId,
        @Valid @RequestBody InfoSuppTypeDTO infoSuppTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InfoSuppType : {}, {}", infoSuppTypeId, infoSuppTypeDTO);
        if (infoSuppTypeDTO.getInfoSuppTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(infoSuppTypeId, infoSuppTypeDTO.getInfoSuppTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoSuppTypeRepository.existsById(infoSuppTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InfoSuppTypeDTO result = infoSuppTypeService.update(infoSuppTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoSuppTypeDTO.getInfoSuppTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /info-supp-types/:infoSuppTypeId} : Partial updates given fields of an existing infoSuppType, field will ignore if it is null
     *
     * @param infoSuppTypeId the id of the infoSuppTypeDTO to save.
     * @param infoSuppTypeDTO the infoSuppTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoSuppTypeDTO,
     * or with status {@code 400 (Bad Request)} if the infoSuppTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the infoSuppTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoSuppTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-supp-types/{infoSuppTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InfoSuppTypeDTO> partialUpdateInfoSuppType(
        @PathVariable(value = "infoSuppTypeId", required = false) final Long infoSuppTypeId,
        @NotNull @RequestBody InfoSuppTypeDTO infoSuppTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoSuppType partially : {}, {}", infoSuppTypeId, infoSuppTypeDTO);
        if (infoSuppTypeDTO.getInfoSuppTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(infoSuppTypeId, infoSuppTypeDTO.getInfoSuppTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoSuppTypeRepository.existsById(infoSuppTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InfoSuppTypeDTO> result = infoSuppTypeService.partialUpdate(infoSuppTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infoSuppTypeDTO.getInfoSuppTypeId().toString())
        );
    }

    /**
     * {@code GET  /info-supp-types} : get all the infoSuppTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoSuppTypes in body.
     */
    @GetMapping("/info-supp-types")
    public ResponseEntity<List<InfoSuppTypeDTO>> getAllInfoSuppTypes(
        InfoSuppTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InfoSuppTypes by criteria: {}", criteria);
        Page<InfoSuppTypeDTO> page = infoSuppTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /info-supp-types/count} : count all the infoSuppTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/info-supp-types/count")
    public ResponseEntity<Long> countInfoSuppTypes(InfoSuppTypeCriteria criteria) {
        log.debug("REST request to count InfoSuppTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(infoSuppTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /info-supp-types/:id} : get the "id" infoSuppType.
     *
     * @param id the id of the infoSuppTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoSuppTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-supp-types/{id}")
    public ResponseEntity<InfoSuppTypeDTO> getInfoSuppType(@PathVariable Long id) {
        log.debug("REST request to get InfoSuppType : {}", id);
        Optional<InfoSuppTypeDTO> infoSuppTypeDTO = infoSuppTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoSuppTypeDTO);
    }

    /**
     * {@code DELETE  /info-supp-types/:id} : delete the "id" infoSuppType.
     *
     * @param id the id of the infoSuppTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-supp-types/{id}")
    public ResponseEntity<Void> deleteInfoSuppType(@PathVariable Long id) {
        log.debug("REST request to delete InfoSuppType : {}", id);
        infoSuppTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
