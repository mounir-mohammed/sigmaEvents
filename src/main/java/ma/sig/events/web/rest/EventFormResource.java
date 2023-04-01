package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.sig.events.repository.EventFormRepository;
import ma.sig.events.service.EventFormQueryService;
import ma.sig.events.service.EventFormService;
import ma.sig.events.service.criteria.EventFormCriteria;
import ma.sig.events.service.dto.EventFormDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.EventForm}.
 */
@RestController
@RequestMapping("/api")
public class EventFormResource {

    private final Logger log = LoggerFactory.getLogger(EventFormResource.class);

    private static final String ENTITY_NAME = "eventForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventFormService eventFormService;

    private final EventFormRepository eventFormRepository;

    private final EventFormQueryService eventFormQueryService;

    public EventFormResource(
        EventFormService eventFormService,
        EventFormRepository eventFormRepository,
        EventFormQueryService eventFormQueryService
    ) {
        this.eventFormService = eventFormService;
        this.eventFormRepository = eventFormRepository;
        this.eventFormQueryService = eventFormQueryService;
    }

    /**
     * {@code POST  /event-forms} : Create a new eventForm.
     *
     * @param eventFormDTO the eventFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventFormDTO, or with status {@code 400 (Bad Request)} if the eventForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-forms")
    public ResponseEntity<EventFormDTO> createEventForm(@RequestBody EventFormDTO eventFormDTO) throws URISyntaxException {
        log.debug("REST request to save EventForm : {}", eventFormDTO);
        if (eventFormDTO.getFormId() != null) {
            throw new BadRequestAlertException("A new eventForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventFormDTO result = eventFormService.save(eventFormDTO);
        return ResponseEntity
            .created(new URI("/api/event-forms/" + result.getFormId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getFormId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-forms/:formId} : Updates an existing eventForm.
     *
     * @param formId the id of the eventFormDTO to save.
     * @param eventFormDTO the eventFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventFormDTO,
     * or with status {@code 400 (Bad Request)} if the eventFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-forms/{formId}")
    public ResponseEntity<EventFormDTO> updateEventForm(
        @PathVariable(value = "formId", required = false) final Long formId,
        @RequestBody EventFormDTO eventFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventForm : {}, {}", formId, eventFormDTO);
        if (eventFormDTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, eventFormDTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventFormRepository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventFormDTO result = eventFormService.update(eventFormDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventFormDTO.getFormId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-forms/:formId} : Partial updates given fields of an existing eventForm, field will ignore if it is null
     *
     * @param formId the id of the eventFormDTO to save.
     * @param eventFormDTO the eventFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventFormDTO,
     * or with status {@code 400 (Bad Request)} if the eventFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-forms/{formId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventFormDTO> partialUpdateEventForm(
        @PathVariable(value = "formId", required = false) final Long formId,
        @RequestBody EventFormDTO eventFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventForm partially : {}, {}", formId, eventFormDTO);
        if (eventFormDTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, eventFormDTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventFormRepository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventFormDTO> result = eventFormService.partialUpdate(eventFormDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventFormDTO.getFormId().toString())
        );
    }

    /**
     * {@code GET  /event-forms} : get all the eventForms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventForms in body.
     */
    @GetMapping("/event-forms")
    public ResponseEntity<List<EventFormDTO>> getAllEventForms(
        EventFormCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventForms by criteria: {}", criteria);
        Page<EventFormDTO> page = eventFormQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-forms/count} : count all the eventForms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-forms/count")
    public ResponseEntity<Long> countEventForms(EventFormCriteria criteria) {
        log.debug("REST request to count EventForms by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventFormQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-forms/:id} : get the "id" eventForm.
     *
     * @param id the id of the eventFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-forms/{id}")
    public ResponseEntity<EventFormDTO> getEventForm(@PathVariable Long id) {
        log.debug("REST request to get EventForm : {}", id);
        Optional<EventFormDTO> eventFormDTO = eventFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventFormDTO);
    }

    /**
     * {@code DELETE  /event-forms/:id} : delete the "id" eventForm.
     *
     * @param id the id of the eventFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-forms/{id}")
    public ResponseEntity<Void> deleteEventForm(@PathVariable Long id) {
        log.debug("REST request to delete EventForm : {}", id);
        eventFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
