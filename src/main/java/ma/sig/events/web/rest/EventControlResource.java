package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.sig.events.repository.EventControlRepository;
import ma.sig.events.service.EventControlQueryService;
import ma.sig.events.service.EventControlService;
import ma.sig.events.service.criteria.EventControlCriteria;
import ma.sig.events.service.dto.EventControlDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.EventControl}.
 */
@RestController
@RequestMapping("/api")
public class EventControlResource {

    private final Logger log = LoggerFactory.getLogger(EventControlResource.class);

    private static final String ENTITY_NAME = "eventControl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventControlService eventControlService;

    private final EventControlRepository eventControlRepository;

    private final EventControlQueryService eventControlQueryService;

    public EventControlResource(
        EventControlService eventControlService,
        EventControlRepository eventControlRepository,
        EventControlQueryService eventControlQueryService
    ) {
        this.eventControlService = eventControlService;
        this.eventControlRepository = eventControlRepository;
        this.eventControlQueryService = eventControlQueryService;
    }

    /**
     * {@code POST  /event-controls} : Create a new eventControl.
     *
     * @param eventControlDTO the eventControlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventControlDTO, or with status {@code 400 (Bad Request)} if the eventControl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-controls")
    public ResponseEntity<EventControlDTO> createEventControl(@RequestBody EventControlDTO eventControlDTO) throws URISyntaxException {
        log.debug("REST request to save EventControl : {}", eventControlDTO);
        if (eventControlDTO.getControlId() != null) {
            throw new BadRequestAlertException("A new eventControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventControlDTO result = eventControlService.save(eventControlDTO);
        return ResponseEntity
            .created(new URI("/api/event-controls/" + result.getControlId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getControlId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-controls/:controlId} : Updates an existing eventControl.
     *
     * @param controlId the id of the eventControlDTO to save.
     * @param eventControlDTO the eventControlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventControlDTO,
     * or with status {@code 400 (Bad Request)} if the eventControlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventControlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-controls/{controlId}")
    public ResponseEntity<EventControlDTO> updateEventControl(
        @PathVariable(value = "controlId", required = false) final Long controlId,
        @RequestBody EventControlDTO eventControlDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventControl : {}, {}", controlId, eventControlDTO);
        if (eventControlDTO.getControlId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(controlId, eventControlDTO.getControlId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventControlRepository.existsById(controlId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventControlDTO result = eventControlService.update(eventControlDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventControlDTO.getControlId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-controls/:controlId} : Partial updates given fields of an existing eventControl, field will ignore if it is null
     *
     * @param controlId the id of the eventControlDTO to save.
     * @param eventControlDTO the eventControlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventControlDTO,
     * or with status {@code 400 (Bad Request)} if the eventControlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventControlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventControlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-controls/{controlId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventControlDTO> partialUpdateEventControl(
        @PathVariable(value = "controlId", required = false) final Long controlId,
        @RequestBody EventControlDTO eventControlDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventControl partially : {}, {}", controlId, eventControlDTO);
        if (eventControlDTO.getControlId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(controlId, eventControlDTO.getControlId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventControlRepository.existsById(controlId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventControlDTO> result = eventControlService.partialUpdate(eventControlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventControlDTO.getControlId().toString())
        );
    }

    /**
     * {@code GET  /event-controls} : get all the eventControls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventControls in body.
     */
    @GetMapping("/event-controls")
    public ResponseEntity<List<EventControlDTO>> getAllEventControls(
        EventControlCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventControls by criteria: {}", criteria);
        Page<EventControlDTO> page = eventControlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-controls/count} : count all the eventControls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-controls/count")
    public ResponseEntity<Long> countEventControls(EventControlCriteria criteria) {
        log.debug("REST request to count EventControls by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventControlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-controls/:id} : get the "id" eventControl.
     *
     * @param id the id of the eventControlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventControlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-controls/{id}")
    public ResponseEntity<EventControlDTO> getEventControl(@PathVariable Long id) {
        log.debug("REST request to get EventControl : {}", id);
        Optional<EventControlDTO> eventControlDTO = eventControlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventControlDTO);
    }

    /**
     * {@code DELETE  /event-controls/:id} : delete the "id" eventControl.
     *
     * @param id the id of the eventControlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-controls/{id}")
    public ResponseEntity<Void> deleteEventControl(@PathVariable Long id) {
        log.debug("REST request to delete EventControl : {}", id);
        eventControlService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
