package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.PrintingServerRepository;
import ma.sig.events.service.PrintingServerQueryService;
import ma.sig.events.service.PrintingServerService;
import ma.sig.events.service.criteria.PrintingServerCriteria;
import ma.sig.events.service.dto.PrintingServerDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.PrintingServer}.
 */
@RestController
@RequestMapping("/api")
public class PrintingServerResource {

    private final Logger log = LoggerFactory.getLogger(PrintingServerResource.class);

    private static final String ENTITY_NAME = "printingServer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrintingServerService printingServerService;

    private final PrintingServerRepository printingServerRepository;

    private final PrintingServerQueryService printingServerQueryService;

    public PrintingServerResource(
        PrintingServerService printingServerService,
        PrintingServerRepository printingServerRepository,
        PrintingServerQueryService printingServerQueryService
    ) {
        this.printingServerService = printingServerService;
        this.printingServerRepository = printingServerRepository;
        this.printingServerQueryService = printingServerQueryService;
    }

    /**
     * {@code POST  /printing-servers} : Create a new printingServer.
     *
     * @param printingServerDTO the printingServerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new printingServerDTO, or with status {@code 400 (Bad Request)} if the printingServer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/printing-servers")
    public ResponseEntity<PrintingServerDTO> createPrintingServer(@Valid @RequestBody PrintingServerDTO printingServerDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrintingServer : {}", printingServerDTO);
        if (printingServerDTO.getPrintingServerId() != null) {
            throw new BadRequestAlertException("A new printingServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrintingServerDTO result = printingServerService.save(printingServerDTO);
        return ResponseEntity
            .created(new URI("/api/printing-servers/" + result.getPrintingServerId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getPrintingServerId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /printing-servers/:printingServerId} : Updates an existing printingServer.
     *
     * @param printingServerId the id of the printingServerDTO to save.
     * @param printingServerDTO the printingServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingServerDTO,
     * or with status {@code 400 (Bad Request)} if the printingServerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the printingServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/printing-servers/{printingServerId}")
    public ResponseEntity<PrintingServerDTO> updatePrintingServer(
        @PathVariable(value = "printingServerId", required = false) final Long printingServerId,
        @Valid @RequestBody PrintingServerDTO printingServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrintingServer : {}, {}", printingServerId, printingServerDTO);
        if (printingServerDTO.getPrintingServerId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingServerId, printingServerDTO.getPrintingServerId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingServerRepository.existsById(printingServerId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrintingServerDTO result = printingServerService.update(printingServerDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingServerDTO.getPrintingServerId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /printing-servers/:printingServerId} : Partial updates given fields of an existing printingServer, field will ignore if it is null
     *
     * @param printingServerId the id of the printingServerDTO to save.
     * @param printingServerDTO the printingServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingServerDTO,
     * or with status {@code 400 (Bad Request)} if the printingServerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the printingServerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the printingServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/printing-servers/{printingServerId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrintingServerDTO> partialUpdatePrintingServer(
        @PathVariable(value = "printingServerId", required = false) final Long printingServerId,
        @NotNull @RequestBody PrintingServerDTO printingServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrintingServer partially : {}, {}", printingServerId, printingServerDTO);
        if (printingServerDTO.getPrintingServerId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingServerId, printingServerDTO.getPrintingServerId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingServerRepository.existsById(printingServerId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrintingServerDTO> result = printingServerService.partialUpdate(printingServerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingServerDTO.getPrintingServerId().toString())
        );
    }

    /**
     * {@code GET  /printing-servers} : get all the printingServers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of printingServers in body.
     */
    @GetMapping("/printing-servers")
    public ResponseEntity<List<PrintingServerDTO>> getAllPrintingServers(
        PrintingServerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrintingServers by criteria: {}", criteria);
        Page<PrintingServerDTO> page = printingServerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /printing-servers/count} : count all the printingServers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/printing-servers/count")
    public ResponseEntity<Long> countPrintingServers(PrintingServerCriteria criteria) {
        log.debug("REST request to count PrintingServers by criteria: {}", criteria);
        return ResponseEntity.ok().body(printingServerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /printing-servers/:id} : get the "id" printingServer.
     *
     * @param id the id of the printingServerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the printingServerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/printing-servers/{id}")
    public ResponseEntity<PrintingServerDTO> getPrintingServer(@PathVariable Long id) {
        log.debug("REST request to get PrintingServer : {}", id);
        Optional<PrintingServerDTO> printingServerDTO = printingServerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(printingServerDTO);
    }

    /**
     * {@code DELETE  /printing-servers/:id} : delete the "id" printingServer.
     *
     * @param id the id of the printingServerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/printing-servers/{id}")
    public ResponseEntity<Void> deletePrintingServer(@PathVariable Long id) {
        log.debug("REST request to delete PrintingServer : {}", id);
        printingServerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
