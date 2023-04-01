package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.PrintingModelRepository;
import ma.sig.events.service.PrintingModelQueryService;
import ma.sig.events.service.PrintingModelService;
import ma.sig.events.service.criteria.PrintingModelCriteria;
import ma.sig.events.service.dto.PrintingModelDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.PrintingModel}.
 */
@RestController
@RequestMapping("/api")
public class PrintingModelResource {

    private final Logger log = LoggerFactory.getLogger(PrintingModelResource.class);

    private static final String ENTITY_NAME = "printingModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrintingModelService printingModelService;

    private final PrintingModelRepository printingModelRepository;

    private final PrintingModelQueryService printingModelQueryService;

    public PrintingModelResource(
        PrintingModelService printingModelService,
        PrintingModelRepository printingModelRepository,
        PrintingModelQueryService printingModelQueryService
    ) {
        this.printingModelService = printingModelService;
        this.printingModelRepository = printingModelRepository;
        this.printingModelQueryService = printingModelQueryService;
    }

    /**
     * {@code POST  /printing-models} : Create a new printingModel.
     *
     * @param printingModelDTO the printingModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new printingModelDTO, or with status {@code 400 (Bad Request)} if the printingModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/printing-models")
    public ResponseEntity<PrintingModelDTO> createPrintingModel(@Valid @RequestBody PrintingModelDTO printingModelDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrintingModel : {}", printingModelDTO);
        if (printingModelDTO.getPrintingModelId() != null) {
            throw new BadRequestAlertException("A new printingModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrintingModelDTO result = printingModelService.save(printingModelDTO);
        return ResponseEntity
            .created(new URI("/api/printing-models/" + result.getPrintingModelId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getPrintingModelId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /printing-models/:printingModelId} : Updates an existing printingModel.
     *
     * @param printingModelId the id of the printingModelDTO to save.
     * @param printingModelDTO the printingModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingModelDTO,
     * or with status {@code 400 (Bad Request)} if the printingModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the printingModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/printing-models/{printingModelId}")
    public ResponseEntity<PrintingModelDTO> updatePrintingModel(
        @PathVariable(value = "printingModelId", required = false) final Long printingModelId,
        @Valid @RequestBody PrintingModelDTO printingModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrintingModel : {}, {}", printingModelId, printingModelDTO);
        if (printingModelDTO.getPrintingModelId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingModelId, printingModelDTO.getPrintingModelId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingModelRepository.existsById(printingModelId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrintingModelDTO result = printingModelService.update(printingModelDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingModelDTO.getPrintingModelId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /printing-models/:printingModelId} : Partial updates given fields of an existing printingModel, field will ignore if it is null
     *
     * @param printingModelId the id of the printingModelDTO to save.
     * @param printingModelDTO the printingModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingModelDTO,
     * or with status {@code 400 (Bad Request)} if the printingModelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the printingModelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the printingModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/printing-models/{printingModelId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrintingModelDTO> partialUpdatePrintingModel(
        @PathVariable(value = "printingModelId", required = false) final Long printingModelId,
        @NotNull @RequestBody PrintingModelDTO printingModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrintingModel partially : {}, {}", printingModelId, printingModelDTO);
        if (printingModelDTO.getPrintingModelId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingModelId, printingModelDTO.getPrintingModelId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingModelRepository.existsById(printingModelId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrintingModelDTO> result = printingModelService.partialUpdate(printingModelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingModelDTO.getPrintingModelId().toString())
        );
    }

    /**
     * {@code GET  /printing-models} : get all the printingModels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of printingModels in body.
     */
    @GetMapping("/printing-models")
    public ResponseEntity<List<PrintingModelDTO>> getAllPrintingModels(
        PrintingModelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrintingModels by criteria: {}", criteria);
        Page<PrintingModelDTO> page = printingModelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /printing-models/count} : count all the printingModels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/printing-models/count")
    public ResponseEntity<Long> countPrintingModels(PrintingModelCriteria criteria) {
        log.debug("REST request to count PrintingModels by criteria: {}", criteria);
        return ResponseEntity.ok().body(printingModelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /printing-models/:id} : get the "id" printingModel.
     *
     * @param id the id of the printingModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the printingModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/printing-models/{id}")
    public ResponseEntity<PrintingModelDTO> getPrintingModel(@PathVariable Long id) {
        log.debug("REST request to get PrintingModel : {}", id);
        Optional<PrintingModelDTO> printingModelDTO = printingModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(printingModelDTO);
    }

    /**
     * {@code DELETE  /printing-models/:id} : delete the "id" printingModel.
     *
     * @param id the id of the printingModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/printing-models/{id}")
    public ResponseEntity<Void> deletePrintingModel(@PathVariable Long id) {
        log.debug("REST request to delete PrintingModel : {}", id);
        printingModelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
