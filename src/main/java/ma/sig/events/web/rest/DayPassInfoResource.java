package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.DayPassInfoRepository;
import ma.sig.events.service.DayPassInfoQueryService;
import ma.sig.events.service.DayPassInfoService;
import ma.sig.events.service.criteria.DayPassInfoCriteria;
import ma.sig.events.service.dto.DayPassInfoDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.DayPassInfo}.
 */
@RestController
@RequestMapping("/api")
public class DayPassInfoResource {

    private final Logger log = LoggerFactory.getLogger(DayPassInfoResource.class);

    private static final String ENTITY_NAME = "dayPassInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayPassInfoService dayPassInfoService;

    private final DayPassInfoRepository dayPassInfoRepository;

    private final DayPassInfoQueryService dayPassInfoQueryService;

    public DayPassInfoResource(
        DayPassInfoService dayPassInfoService,
        DayPassInfoRepository dayPassInfoRepository,
        DayPassInfoQueryService dayPassInfoQueryService
    ) {
        this.dayPassInfoService = dayPassInfoService;
        this.dayPassInfoRepository = dayPassInfoRepository;
        this.dayPassInfoQueryService = dayPassInfoQueryService;
    }

    /**
     * {@code POST  /day-pass-infos} : Create a new dayPassInfo.
     *
     * @param dayPassInfoDTO the dayPassInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dayPassInfoDTO, or with status {@code 400 (Bad Request)} if the dayPassInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/day-pass-infos")
    public ResponseEntity<DayPassInfoDTO> createDayPassInfo(@Valid @RequestBody DayPassInfoDTO dayPassInfoDTO) throws URISyntaxException {
        log.debug("REST request to save DayPassInfo : {}", dayPassInfoDTO);
        if (dayPassInfoDTO.getDayPassInfoId() != null) {
            throw new BadRequestAlertException("A new dayPassInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayPassInfoDTO result = dayPassInfoService.save(dayPassInfoDTO);
        return ResponseEntity
            .created(new URI("/api/day-pass-infos/" + result.getDayPassInfoId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getDayPassInfoId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /day-pass-infos/:dayPassInfoId} : Updates an existing dayPassInfo.
     *
     * @param dayPassInfoId the id of the dayPassInfoDTO to save.
     * @param dayPassInfoDTO the dayPassInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayPassInfoDTO,
     * or with status {@code 400 (Bad Request)} if the dayPassInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dayPassInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/day-pass-infos/{dayPassInfoId}")
    public ResponseEntity<DayPassInfoDTO> updateDayPassInfo(
        @PathVariable(value = "dayPassInfoId", required = false) final Long dayPassInfoId,
        @Valid @RequestBody DayPassInfoDTO dayPassInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DayPassInfo : {}, {}", dayPassInfoId, dayPassInfoDTO);
        if (dayPassInfoDTO.getDayPassInfoId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(dayPassInfoId, dayPassInfoDTO.getDayPassInfoId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dayPassInfoRepository.existsById(dayPassInfoId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DayPassInfoDTO result = dayPassInfoService.update(dayPassInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayPassInfoDTO.getDayPassInfoId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /day-pass-infos/:dayPassInfoId} : Partial updates given fields of an existing dayPassInfo, field will ignore if it is null
     *
     * @param dayPassInfoId the id of the dayPassInfoDTO to save.
     * @param dayPassInfoDTO the dayPassInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayPassInfoDTO,
     * or with status {@code 400 (Bad Request)} if the dayPassInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dayPassInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dayPassInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/day-pass-infos/{dayPassInfoId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DayPassInfoDTO> partialUpdateDayPassInfo(
        @PathVariable(value = "dayPassInfoId", required = false) final Long dayPassInfoId,
        @NotNull @RequestBody DayPassInfoDTO dayPassInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DayPassInfo partially : {}, {}", dayPassInfoId, dayPassInfoDTO);
        if (dayPassInfoDTO.getDayPassInfoId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(dayPassInfoId, dayPassInfoDTO.getDayPassInfoId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dayPassInfoRepository.existsById(dayPassInfoId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DayPassInfoDTO> result = dayPassInfoService.partialUpdate(dayPassInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayPassInfoDTO.getDayPassInfoId().toString())
        );
    }

    /**
     * {@code GET  /day-pass-infos} : get all the dayPassInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dayPassInfos in body.
     */
    @GetMapping("/day-pass-infos")
    public ResponseEntity<List<DayPassInfoDTO>> getAllDayPassInfos(
        DayPassInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DayPassInfos by criteria: {}", criteria);
        Page<DayPassInfoDTO> page = dayPassInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /day-pass-infos/count} : count all the dayPassInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/day-pass-infos/count")
    public ResponseEntity<Long> countDayPassInfos(DayPassInfoCriteria criteria) {
        log.debug("REST request to count DayPassInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(dayPassInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /day-pass-infos/:id} : get the "id" dayPassInfo.
     *
     * @param id the id of the dayPassInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dayPassInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/day-pass-infos/{id}")
    public ResponseEntity<DayPassInfoDTO> getDayPassInfo(@PathVariable Long id) {
        log.debug("REST request to get DayPassInfo : {}", id);
        Optional<DayPassInfoDTO> dayPassInfoDTO = dayPassInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dayPassInfoDTO);
    }

    /**
     * {@code DELETE  /day-pass-infos/:id} : delete the "id" dayPassInfo.
     *
     * @param id the id of the dayPassInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/day-pass-infos/{id}")
    public ResponseEntity<Void> deleteDayPassInfo(@PathVariable Long id) {
        log.debug("REST request to delete DayPassInfo : {}", id);
        dayPassInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
