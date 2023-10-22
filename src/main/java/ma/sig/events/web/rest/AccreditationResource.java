package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.AccreditationRepository;
import ma.sig.events.service.AccreditationQueryService;
import ma.sig.events.service.AccreditationService;
import ma.sig.events.service.criteria.AccreditationCriteria;
import ma.sig.events.service.dto.AccreditationDTO;
import ma.sig.events.service.dto.MassUpdateAccreditationDTO;
import ma.sig.events.service.utils.AccreditationHeaderUtil;
import ma.sig.events.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
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
 * REST controller for managing {@link ma.sig.events.domain.Accreditation}.
 */
@RestController
@RequestMapping("/api")
public class AccreditationResource {

    private final Logger log = LoggerFactory.getLogger(AccreditationResource.class);

    private static final String ENTITY_NAME = "accreditation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccreditationService accreditationService;

    private final AccreditationRepository accreditationRepository;

    private final AccreditationQueryService accreditationQueryService;

    public AccreditationResource(
        AccreditationService accreditationService,
        AccreditationRepository accreditationRepository,
        AccreditationQueryService accreditationQueryService
    ) {
        this.accreditationService = accreditationService;
        this.accreditationRepository = accreditationRepository;
        this.accreditationQueryService = accreditationQueryService;
    }

    /**
     * {@code POST  /accreditations} : Create a new accreditation.
     *
     * @param accreditationDTO the accreditationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accreditationDTO, or with status {@code 400 (Bad Request)} if the accreditation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accreditations")
    public ResponseEntity<AccreditationDTO> createAccreditation(@Valid @RequestBody AccreditationDTO accreditationDTO)
        throws URISyntaxException {
        log.debug("REST request to save Accreditation : {}", accreditationDTO);
        if (accreditationDTO.getAccreditationId() != null) {
            throw new BadRequestAlertException("A new accreditation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccreditationDTO result = accreditationService.save(accreditationDTO);
        return ResponseEntity
            .created(new URI("/api/accreditations/" + result.getAccreditationId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getAccreditationId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accreditations/:accreditationId} : Updates an existing accreditation.
     *
     * @param accreditationId the id of the accreditationDTO to save.
     * @param accreditationDTO the accreditationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accreditations/{accreditationId}")
    public ResponseEntity<AccreditationDTO> updateAccreditation(
        @PathVariable(value = "accreditationId", required = false) final Long accreditationId,
        @Valid @RequestBody AccreditationDTO accreditationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Accreditation : {}, {}", accreditationId, accreditationDTO);
        if (accreditationDTO.getAccreditationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accreditationId, accreditationDTO.getAccreditationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accreditationRepository.existsById(accreditationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccreditationDTO result = accreditationService.update(accreditationDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accreditationDTO.getAccreditationId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /accreditations/:accreditationId} : Partial updates given fields of an existing accreditation, field will ignore if it is null
     *
     * @param accreditationId the id of the accreditationDTO to save.
     * @param accreditationDTO the accreditationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accreditationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accreditations/{accreditationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccreditationDTO> partialUpdateAccreditation(
        @PathVariable(value = "accreditationId", required = false) final Long accreditationId,
        @NotNull @RequestBody AccreditationDTO accreditationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accreditation partially : {}, {}", accreditationId, accreditationDTO);
        if (accreditationDTO.getAccreditationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(accreditationId, accreditationDTO.getAccreditationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accreditationRepository.existsById(accreditationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccreditationDTO> result = accreditationService.partialUpdate(accreditationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accreditationDTO.getAccreditationId().toString())
        );
    }

    /**
     * {@code GET  /accreditations} : get all the accreditations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accreditations in body.
     */
    @GetMapping("/accreditations")
    public ResponseEntity<List<AccreditationDTO>> getAllAccreditations(
        AccreditationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        String searchText
    ) {
        log.debug(searchText);
        Page<AccreditationDTO> page;
        log.debug("REST request to get Accreditations by criteria: {}", criteria);
        if (StringUtils.isNotBlank(searchText)) {
            page = accreditationQueryService.findByCriteriaForSearch(criteria, pageable, searchText);
        } else {
            page = accreditationQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accreditations/count} : count all the accreditations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accreditations/count")
    public ResponseEntity<Long> countAccreditations(AccreditationCriteria criteria) {
        log.debug("REST request to count Accreditations by criteria: {}", criteria);
        return ResponseEntity.ok().body(accreditationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accreditations/:id} : get the "id" accreditation.
     *
     * @param id the id of the accreditationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accreditationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accreditations/{id}")
    public ResponseEntity<AccreditationDTO> getAccreditation(@PathVariable Long id) {
        log.debug("REST request to get Accreditation : {}", id);
        Optional<AccreditationDTO> accreditationDTO = accreditationQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(accreditationDTO);
    }

    /**
     * {@code DELETE  /accreditations/:id} : delete the "id" accreditation.
     *
     * @param id the id of the accreditationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accreditations/{id}")
    public ResponseEntity<Void> deleteAccreditation(@PathVariable Long id) {
        log.debug("REST request to delete Accreditation : {}", id);
        accreditationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code PUT  /accreditations/{accreditationId}/status/{statusId}/validate}
     *
     * @param accreditationId the id of the accreditationDTO to save.
     * @param statusId the id of the statusDTO to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accreditationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/accreditations/{accreditationId}/status/{statusId}/validate")
    public ResponseEntity<AccreditationDTO> validate(
        @PathVariable(value = "accreditationId") final Long accreditationId,
        @PathVariable(value = "statusId", required = true) final Long statusId,
        @RequestBody(required = false) AccreditationDTO accreditationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accreditation partially : {}, {}", accreditationId);
        if (accreditationId == null) {
            throw new BadRequestAlertException("Invalid accreditationId", ENTITY_NAME, "idnull");
        }
        if (statusId == null) {
            throw new BadRequestAlertException("Invalid statusId", ENTITY_NAME, "idnull");
        }

        Optional<AccreditationDTO> result = accreditationService.validateAccreditation(accreditationId, statusId);

        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code PUT  /accreditations/{accreditationId}/status/{statusId}/validate}
     *
     * @param accreditationId the id of the accreditationDTO to save.
     * @param statusId the id of the statusDTO to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accreditationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/accreditations/{accreditationId}/status/{statusId}/print")
    public ResponseEntity<AccreditationDTO> print(
        @PathVariable(value = "accreditationId") final Long accreditationId,
        @PathVariable(value = "statusId", required = true) final Long statusId,
        @RequestBody(required = false) AccreditationDTO accreditationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accreditation partially : {}, {}", accreditationId);
        if (accreditationId == null) {
            throw new BadRequestAlertException("Invalid accreditationId", ENTITY_NAME, "idnull");
        }
        if (statusId == null) {
            throw new BadRequestAlertException("Invalid statusId", ENTITY_NAME, "idnull");
        }

        Optional<AccreditationDTO> result = accreditationService.printAccreditation(accreditationId, statusId);

        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code PUT  /accreditations/{accreditationId}/status/{statusId}/validate}
     *
     * @param accreditationId the ids of the accreditationDTOs to save.
     * @param statusId        the id of the statusDTO to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accreditationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/accreditations/{accreditationId}/status/{statusId}/massprint")
    public ResponseEntity<Boolean> massPrint(
        @PathVariable(value = "accreditationId") final Long[] accreditationId,
        @PathVariable(value = "statusId", required = true) final Long statusId,
        @RequestBody(required = false) AccreditationDTO accreditationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accreditation partially : {}, {}", accreditationId);
        if (accreditationId == null) {
            throw new BadRequestAlertException("Invalid accreditationIds", ENTITY_NAME, "idnull");
        }
        if (statusId == null) {
            throw new BadRequestAlertException("Invalid statusId", ENTITY_NAME, "idnull");
        }
        Optional<Boolean> result = accreditationService.massPrintAccreditation(accreditationId, statusId);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code PATCH  /accreditations/massUpdate} : Mass Partial updates given fields of an existing accreditation, field will ignore if it is null
     *
     * @param accreditations the accreditationDTO List to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stat,
     * or with status {@code 500 (Internal Server Error)} if the accreditations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/accreditations/massUpdate")
    public ResponseEntity<Boolean> massUpdateAccreditations(
        @RequestBody(required = true) MassUpdateAccreditationDTO massUpdateAccreditationDTO
    ) {
        int size = massUpdateAccreditationDTO.getAccreditationIds().size();
        log.debug("REST request to mass update Accreditations partially size: {}", size);

        Optional<Boolean> result = accreditationService.massUpdate(massUpdateAccreditationDTO);

        if (size == 0) {
            log.debug("REST request to mass update Accreditations partially : No Accreditation Found");
        }

        return ResponseUtil.wrapOrNotFound(
            result,
            AccreditationHeaderUtil.createEntityMassUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(size))
        );
    }
}
