package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.PrintingCentreRepository;
import ma.sig.events.service.PrintingCentreQueryService;
import ma.sig.events.service.PrintingCentreService;
import ma.sig.events.service.criteria.PrintingCentreCriteria;
import ma.sig.events.service.dto.PrintingCentreDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.PrintingCentre}.
 */
@RestController
@RequestMapping("/api")
public class PrintingCentreResource {

    private final Logger log = LoggerFactory.getLogger(PrintingCentreResource.class);

    private static final String ENTITY_NAME = "printingCentre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrintingCentreService printingCentreService;

    private final PrintingCentreRepository printingCentreRepository;

    private final PrintingCentreQueryService printingCentreQueryService;

    public PrintingCentreResource(
        PrintingCentreService printingCentreService,
        PrintingCentreRepository printingCentreRepository,
        PrintingCentreQueryService printingCentreQueryService
    ) {
        this.printingCentreService = printingCentreService;
        this.printingCentreRepository = printingCentreRepository;
        this.printingCentreQueryService = printingCentreQueryService;
    }

    /**
     * {@code POST  /printing-centres} : Create a new printingCentre.
     *
     * @param printingCentreDTO the printingCentreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new printingCentreDTO, or with status {@code 400 (Bad Request)} if the printingCentre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/printing-centres")
    public ResponseEntity<PrintingCentreDTO> createPrintingCentre(@Valid @RequestBody PrintingCentreDTO printingCentreDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrintingCentre : {}", printingCentreDTO);
        if (printingCentreDTO.getPrintingCentreId() != null) {
            throw new BadRequestAlertException("A new printingCentre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrintingCentreDTO result = printingCentreService.save(printingCentreDTO);
        return ResponseEntity
            .created(new URI("/api/printing-centres/" + result.getPrintingCentreId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getPrintingCentreId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /printing-centres/:printingCentreId} : Updates an existing printingCentre.
     *
     * @param printingCentreId the id of the printingCentreDTO to save.
     * @param printingCentreDTO the printingCentreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingCentreDTO,
     * or with status {@code 400 (Bad Request)} if the printingCentreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the printingCentreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/printing-centres/{printingCentreId}")
    public ResponseEntity<PrintingCentreDTO> updatePrintingCentre(
        @PathVariable(value = "printingCentreId", required = false) final Long printingCentreId,
        @Valid @RequestBody PrintingCentreDTO printingCentreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrintingCentre : {}, {}", printingCentreId, printingCentreDTO);
        if (printingCentreDTO.getPrintingCentreId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingCentreId, printingCentreDTO.getPrintingCentreId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingCentreRepository.existsById(printingCentreId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrintingCentreDTO result = printingCentreService.update(printingCentreDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingCentreDTO.getPrintingCentreId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /printing-centres/:printingCentreId} : Partial updates given fields of an existing printingCentre, field will ignore if it is null
     *
     * @param printingCentreId the id of the printingCentreDTO to save.
     * @param printingCentreDTO the printingCentreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingCentreDTO,
     * or with status {@code 400 (Bad Request)} if the printingCentreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the printingCentreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the printingCentreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/printing-centres/{printingCentreId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrintingCentreDTO> partialUpdatePrintingCentre(
        @PathVariable(value = "printingCentreId", required = false) final Long printingCentreId,
        @NotNull @RequestBody PrintingCentreDTO printingCentreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrintingCentre partially : {}, {}", printingCentreId, printingCentreDTO);
        if (printingCentreDTO.getPrintingCentreId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingCentreId, printingCentreDTO.getPrintingCentreId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingCentreRepository.existsById(printingCentreId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrintingCentreDTO> result = printingCentreService.partialUpdate(printingCentreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingCentreDTO.getPrintingCentreId().toString())
        );
    }

    /**
     * {@code GET  /printing-centres} : get all the printingCentres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of printingCentres in body.
     */
    @GetMapping("/printing-centres")
    public ResponseEntity<List<PrintingCentreDTO>> getAllPrintingCentres(
        PrintingCentreCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrintingCentres by criteria: {}", criteria);
        Page<PrintingCentreDTO> page = printingCentreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /printing-centres/count} : count all the printingCentres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/printing-centres/count")
    public ResponseEntity<Long> countPrintingCentres(PrintingCentreCriteria criteria) {
        log.debug("REST request to count PrintingCentres by criteria: {}", criteria);
        return ResponseEntity.ok().body(printingCentreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /printing-centres/:id} : get the "id" printingCentre.
     *
     * @param id the id of the printingCentreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the printingCentreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/printing-centres/{id}")
    public ResponseEntity<PrintingCentreDTO> getPrintingCentre(@PathVariable Long id) {
        log.debug("REST request to get PrintingCentre : {}", id);
        Optional<PrintingCentreDTO> printingCentreDTO = printingCentreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(printingCentreDTO);
    }

    /**
     * {@code DELETE  /printing-centres/:id} : delete the "id" printingCentre.
     *
     * @param id the id of the printingCentreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/printing-centres/{id}")
    public ResponseEntity<Void> deletePrintingCentre(@PathVariable Long id) {
        log.debug("REST request to delete PrintingCentre : {}", id);
        printingCentreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
