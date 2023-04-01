package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.PrintingTypeRepository;
import ma.sig.events.service.PrintingTypeQueryService;
import ma.sig.events.service.PrintingTypeService;
import ma.sig.events.service.criteria.PrintingTypeCriteria;
import ma.sig.events.service.dto.PrintingTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.PrintingType}.
 */
@RestController
@RequestMapping("/api")
public class PrintingTypeResource {

    private final Logger log = LoggerFactory.getLogger(PrintingTypeResource.class);

    private static final String ENTITY_NAME = "printingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrintingTypeService printingTypeService;

    private final PrintingTypeRepository printingTypeRepository;

    private final PrintingTypeQueryService printingTypeQueryService;

    public PrintingTypeResource(
        PrintingTypeService printingTypeService,
        PrintingTypeRepository printingTypeRepository,
        PrintingTypeQueryService printingTypeQueryService
    ) {
        this.printingTypeService = printingTypeService;
        this.printingTypeRepository = printingTypeRepository;
        this.printingTypeQueryService = printingTypeQueryService;
    }

    /**
     * {@code POST  /printing-types} : Create a new printingType.
     *
     * @param printingTypeDTO the printingTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new printingTypeDTO, or with status {@code 400 (Bad Request)} if the printingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/printing-types")
    public ResponseEntity<PrintingTypeDTO> createPrintingType(@Valid @RequestBody PrintingTypeDTO printingTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrintingType : {}", printingTypeDTO);
        if (printingTypeDTO.getPrintingTypeId() != null) {
            throw new BadRequestAlertException("A new printingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrintingTypeDTO result = printingTypeService.save(printingTypeDTO);
        return ResponseEntity
            .created(new URI("/api/printing-types/" + result.getPrintingTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getPrintingTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /printing-types/:printingTypeId} : Updates an existing printingType.
     *
     * @param printingTypeId the id of the printingTypeDTO to save.
     * @param printingTypeDTO the printingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the printingTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the printingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/printing-types/{printingTypeId}")
    public ResponseEntity<PrintingTypeDTO> updatePrintingType(
        @PathVariable(value = "printingTypeId", required = false) final Long printingTypeId,
        @Valid @RequestBody PrintingTypeDTO printingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrintingType : {}, {}", printingTypeId, printingTypeDTO);
        if (printingTypeDTO.getPrintingTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingTypeId, printingTypeDTO.getPrintingTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingTypeRepository.existsById(printingTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrintingTypeDTO result = printingTypeService.update(printingTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingTypeDTO.getPrintingTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /printing-types/:printingTypeId} : Partial updates given fields of an existing printingType, field will ignore if it is null
     *
     * @param printingTypeId the id of the printingTypeDTO to save.
     * @param printingTypeDTO the printingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated printingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the printingTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the printingTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the printingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/printing-types/{printingTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrintingTypeDTO> partialUpdatePrintingType(
        @PathVariable(value = "printingTypeId", required = false) final Long printingTypeId,
        @NotNull @RequestBody PrintingTypeDTO printingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrintingType partially : {}, {}", printingTypeId, printingTypeDTO);
        if (printingTypeDTO.getPrintingTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(printingTypeId, printingTypeDTO.getPrintingTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!printingTypeRepository.existsById(printingTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrintingTypeDTO> result = printingTypeService.partialUpdate(printingTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, printingTypeDTO.getPrintingTypeId().toString())
        );
    }

    /**
     * {@code GET  /printing-types} : get all the printingTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of printingTypes in body.
     */
    @GetMapping("/printing-types")
    public ResponseEntity<List<PrintingTypeDTO>> getAllPrintingTypes(
        PrintingTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrintingTypes by criteria: {}", criteria);
        Page<PrintingTypeDTO> page = printingTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /printing-types/count} : count all the printingTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/printing-types/count")
    public ResponseEntity<Long> countPrintingTypes(PrintingTypeCriteria criteria) {
        log.debug("REST request to count PrintingTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(printingTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /printing-types/:id} : get the "id" printingType.
     *
     * @param id the id of the printingTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the printingTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/printing-types/{id}")
    public ResponseEntity<PrintingTypeDTO> getPrintingType(@PathVariable Long id) {
        log.debug("REST request to get PrintingType : {}", id);
        Optional<PrintingTypeDTO> printingTypeDTO = printingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(printingTypeDTO);
    }

    /**
     * {@code DELETE  /printing-types/:id} : delete the "id" printingType.
     *
     * @param id the id of the printingTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/printing-types/{id}")
    public ResponseEntity<Void> deletePrintingType(@PathVariable Long id) {
        log.debug("REST request to delete PrintingType : {}", id);
        printingTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
