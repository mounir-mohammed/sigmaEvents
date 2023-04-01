package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.sig.events.repository.EventFieldRepository;
import ma.sig.events.service.EventFieldQueryService;
import ma.sig.events.service.EventFieldService;
import ma.sig.events.service.criteria.EventFieldCriteria;
import ma.sig.events.service.dto.EventFieldDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.EventField}.
 */
@RestController
@RequestMapping("/api")
public class EventFieldResource {

    private final Logger log = LoggerFactory.getLogger(EventFieldResource.class);

    private static final String ENTITY_NAME = "eventField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventFieldService eventFieldService;

    private final EventFieldRepository eventFieldRepository;

    private final EventFieldQueryService eventFieldQueryService;

    public EventFieldResource(
        EventFieldService eventFieldService,
        EventFieldRepository eventFieldRepository,
        EventFieldQueryService eventFieldQueryService
    ) {
        this.eventFieldService = eventFieldService;
        this.eventFieldRepository = eventFieldRepository;
        this.eventFieldQueryService = eventFieldQueryService;
    }

    /**
     * {@code POST  /event-fields} : Create a new eventField.
     *
     * @param eventFieldDTO the eventFieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventFieldDTO, or with status {@code 400 (Bad Request)} if the eventField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-fields")
    public ResponseEntity<EventFieldDTO> createEventField(@RequestBody EventFieldDTO eventFieldDTO) throws URISyntaxException {
        log.debug("REST request to save EventField : {}", eventFieldDTO);
        if (eventFieldDTO.getFieldId() != null) {
            throw new BadRequestAlertException("A new eventField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventFieldDTO result = eventFieldService.save(eventFieldDTO);
        return ResponseEntity
            .created(new URI("/api/event-fields/" + result.getFieldId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getFieldId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-fields/:fieldId} : Updates an existing eventField.
     *
     * @param fieldId the id of the eventFieldDTO to save.
     * @param eventFieldDTO the eventFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventFieldDTO,
     * or with status {@code 400 (Bad Request)} if the eventFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-fields/{fieldId}")
    public ResponseEntity<EventFieldDTO> updateEventField(
        @PathVariable(value = "fieldId", required = false) final Long fieldId,
        @RequestBody EventFieldDTO eventFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventField : {}, {}", fieldId, eventFieldDTO);
        if (eventFieldDTO.getFieldId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(fieldId, eventFieldDTO.getFieldId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventFieldRepository.existsById(fieldId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventFieldDTO result = eventFieldService.update(eventFieldDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventFieldDTO.getFieldId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-fields/:fieldId} : Partial updates given fields of an existing eventField, field will ignore if it is null
     *
     * @param fieldId the id of the eventFieldDTO to save.
     * @param eventFieldDTO the eventFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventFieldDTO,
     * or with status {@code 400 (Bad Request)} if the eventFieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventFieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-fields/{fieldId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventFieldDTO> partialUpdateEventField(
        @PathVariable(value = "fieldId", required = false) final Long fieldId,
        @RequestBody EventFieldDTO eventFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventField partially : {}, {}", fieldId, eventFieldDTO);
        if (eventFieldDTO.getFieldId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(fieldId, eventFieldDTO.getFieldId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventFieldRepository.existsById(fieldId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventFieldDTO> result = eventFieldService.partialUpdate(eventFieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventFieldDTO.getFieldId().toString())
        );
    }

    /**
     * {@code GET  /event-fields} : get all the eventFields.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventFields in body.
     */
    @GetMapping("/event-fields")
    public ResponseEntity<List<EventFieldDTO>> getAllEventFields(
        EventFieldCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventFields by criteria: {}", criteria);
        Page<EventFieldDTO> page = eventFieldQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-fields/count} : count all the eventFields.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-fields/count")
    public ResponseEntity<Long> countEventFields(EventFieldCriteria criteria) {
        log.debug("REST request to count EventFields by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventFieldQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-fields/:id} : get the "id" eventField.
     *
     * @param id the id of the eventFieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventFieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-fields/{id}")
    public ResponseEntity<EventFieldDTO> getEventField(@PathVariable Long id) {
        log.debug("REST request to get EventField : {}", id);
        Optional<EventFieldDTO> eventFieldDTO = eventFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventFieldDTO);
    }

    /**
     * {@code DELETE  /event-fields/:id} : delete the "id" eventField.
     *
     * @param id the id of the eventFieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-fields/{id}")
    public ResponseEntity<Void> deleteEventField(@PathVariable Long id) {
        log.debug("REST request to delete EventField : {}", id);
        eventFieldService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
