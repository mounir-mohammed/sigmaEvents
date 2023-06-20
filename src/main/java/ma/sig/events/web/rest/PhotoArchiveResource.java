package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.PhotoArchiveRepository;
import ma.sig.events.service.PhotoArchiveQueryService;
import ma.sig.events.service.PhotoArchiveService;
import ma.sig.events.service.criteria.PhotoArchiveCriteria;
import ma.sig.events.service.dto.PhotoArchiveDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.PhotoArchive}.
 */
@RestController
@RequestMapping("/api")
public class PhotoArchiveResource {

    private final Logger log = LoggerFactory.getLogger(PhotoArchiveResource.class);

    private static final String ENTITY_NAME = "photoArchive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotoArchiveService photoArchiveService;

    private final PhotoArchiveRepository photoArchiveRepository;

    private final PhotoArchiveQueryService photoArchiveQueryService;

    public PhotoArchiveResource(
        PhotoArchiveService photoArchiveService,
        PhotoArchiveRepository photoArchiveRepository,
        PhotoArchiveQueryService photoArchiveQueryService
    ) {
        this.photoArchiveService = photoArchiveService;
        this.photoArchiveRepository = photoArchiveRepository;
        this.photoArchiveQueryService = photoArchiveQueryService;
    }

    /**
     * {@code POST  /photo-archives} : Create a new photoArchive.
     *
     * @param photoArchiveDTO the photoArchiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photoArchiveDTO, or with status {@code 400 (Bad Request)} if the photoArchive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/photo-archives")
    public ResponseEntity<PhotoArchiveDTO> createPhotoArchive(@Valid @RequestBody PhotoArchiveDTO photoArchiveDTO)
        throws URISyntaxException {
        log.debug("REST request to save PhotoArchive : {}", photoArchiveDTO);
        if (photoArchiveDTO.getPhotoArchiveId() != null) {
            throw new BadRequestAlertException("A new photoArchive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotoArchiveDTO result = photoArchiveService.save(photoArchiveDTO);
        return ResponseEntity
            .created(new URI("/api/photo-archives/" + result.getPhotoArchiveId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getPhotoArchiveId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /photo-archives/:photoArchiveId} : Updates an existing photoArchive.
     *
     * @param photoArchiveId the id of the photoArchiveDTO to save.
     * @param photoArchiveDTO the photoArchiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoArchiveDTO,
     * or with status {@code 400 (Bad Request)} if the photoArchiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photoArchiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/photo-archives/{photoArchiveId}")
    public ResponseEntity<PhotoArchiveDTO> updatePhotoArchive(
        @PathVariable(value = "photoArchiveId", required = false) final Long photoArchiveId,
        @Valid @RequestBody PhotoArchiveDTO photoArchiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PhotoArchive : {}, {}", photoArchiveId, photoArchiveDTO);
        if (photoArchiveDTO.getPhotoArchiveId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(photoArchiveId, photoArchiveDTO.getPhotoArchiveId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoArchiveRepository.existsById(photoArchiveId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhotoArchiveDTO result = photoArchiveService.update(photoArchiveDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photoArchiveDTO.getPhotoArchiveId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /photo-archives/:photoArchiveId} : Partial updates given fields of an existing photoArchive, field will ignore if it is null
     *
     * @param photoArchiveId the id of the photoArchiveDTO to save.
     * @param photoArchiveDTO the photoArchiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photoArchiveDTO,
     * or with status {@code 400 (Bad Request)} if the photoArchiveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the photoArchiveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the photoArchiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/photo-archives/{photoArchiveId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhotoArchiveDTO> partialUpdatePhotoArchive(
        @PathVariable(value = "photoArchiveId", required = false) final Long photoArchiveId,
        @NotNull @RequestBody PhotoArchiveDTO photoArchiveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhotoArchive partially : {}, {}", photoArchiveId, photoArchiveDTO);
        if (photoArchiveDTO.getPhotoArchiveId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(photoArchiveId, photoArchiveDTO.getPhotoArchiveId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!photoArchiveRepository.existsById(photoArchiveId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhotoArchiveDTO> result = photoArchiveService.partialUpdate(photoArchiveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photoArchiveDTO.getPhotoArchiveId().toString())
        );
    }

    /**
     * {@code GET  /photo-archives} : get all the photoArchives.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photoArchives in body.
     */
    @GetMapping("/photo-archives")
    public ResponseEntity<List<PhotoArchiveDTO>> getAllPhotoArchives(
        PhotoArchiveCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PhotoArchives by criteria: {}", criteria);
        Page<PhotoArchiveDTO> page = photoArchiveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /photo-archives/count} : count all the photoArchives.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/photo-archives/count")
    public ResponseEntity<Long> countPhotoArchives(PhotoArchiveCriteria criteria) {
        log.debug("REST request to count PhotoArchives by criteria: {}", criteria);
        return ResponseEntity.ok().body(photoArchiveQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /photo-archives/:id} : get the "id" photoArchive.
     *
     * @param id the id of the photoArchiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photoArchiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/photo-archives/{id}")
    public ResponseEntity<PhotoArchiveDTO> getPhotoArchive(@PathVariable Long id) {
        log.debug("REST request to get PhotoArchive : {}", id);
        Optional<PhotoArchiveDTO> photoArchiveDTO = photoArchiveQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(photoArchiveDTO);
    }

    /**
     * {@code DELETE  /photo-archives/:id} : delete the "id" photoArchive.
     *
     * @param id the id of the photoArchiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/photo-archives/{id}")
    public ResponseEntity<Void> deletePhotoArchive(@PathVariable Long id) {
        log.debug("REST request to delete PhotoArchive : {}", id);
        photoArchiveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
