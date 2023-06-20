package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.CodeRepository;
import ma.sig.events.service.CodeQueryService;
import ma.sig.events.service.CodeService;
import ma.sig.events.service.criteria.CodeCriteria;
import ma.sig.events.service.dto.CodeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.Code}.
 */
@RestController
@RequestMapping("/api")
public class CodeResource {

    private final Logger log = LoggerFactory.getLogger(CodeResource.class);

    private static final String ENTITY_NAME = "code";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeService codeService;

    private final CodeRepository codeRepository;

    private final CodeQueryService codeQueryService;

    public CodeResource(CodeService codeService, CodeRepository codeRepository, CodeQueryService codeQueryService) {
        this.codeService = codeService;
        this.codeRepository = codeRepository;
        this.codeQueryService = codeQueryService;
    }

    /**
     * {@code POST  /codes} : Create a new code.
     *
     * @param codeDTO the codeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeDTO, or with status {@code 400 (Bad Request)} if the code has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/codes")
    public ResponseEntity<CodeDTO> createCode(@Valid @RequestBody CodeDTO codeDTO) throws URISyntaxException {
        log.debug("REST request to save Code : {}", codeDTO);
        if (codeDTO.getCodeId() != null) {
            throw new BadRequestAlertException("A new code cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeDTO result = codeService.save(codeDTO);
        return ResponseEntity
            .created(new URI("/api/codes/" + result.getCodeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCodeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /codes/:codeId} : Updates an existing code.
     *
     * @param codeId the id of the codeDTO to save.
     * @param codeDTO the codeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeDTO,
     * or with status {@code 400 (Bad Request)} if the codeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/codes/{codeId}")
    public ResponseEntity<CodeDTO> updateCode(
        @PathVariable(value = "codeId", required = false) final Long codeId,
        @Valid @RequestBody CodeDTO codeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Code : {}, {}", codeId, codeDTO);
        if (codeDTO.getCodeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codeId, codeDTO.getCodeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRepository.existsById(codeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CodeDTO result = codeService.update(codeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeDTO.getCodeId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /codes/:codeId} : Partial updates given fields of an existing code, field will ignore if it is null
     *
     * @param codeId the id of the codeDTO to save.
     * @param codeDTO the codeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeDTO,
     * or with status {@code 400 (Bad Request)} if the codeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the codeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the codeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/codes/{codeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodeDTO> partialUpdateCode(
        @PathVariable(value = "codeId", required = false) final Long codeId,
        @NotNull @RequestBody CodeDTO codeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Code partially : {}, {}", codeId, codeDTO);
        if (codeDTO.getCodeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codeId, codeDTO.getCodeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeRepository.existsById(codeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodeDTO> result = codeService.partialUpdate(codeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeDTO.getCodeId().toString())
        );
    }

    /**
     * {@code GET  /codes} : get all the codes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codes in body.
     */
    @GetMapping("/codes")
    public ResponseEntity<List<CodeDTO>> getAllCodes(
        CodeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Codes by criteria: {}", criteria);
        Page<CodeDTO> page = codeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /codes/count} : count all the codes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/codes/count")
    public ResponseEntity<Long> countCodes(CodeCriteria criteria) {
        log.debug("REST request to count Codes by criteria: {}", criteria);
        return ResponseEntity.ok().body(codeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /codes/:id} : get the "id" code.
     *
     * @param id the id of the codeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/codes/{id}")
    public ResponseEntity<CodeDTO> getCode(@PathVariable Long id) {
        log.debug("REST request to get Code : {}", id);
        Optional<CodeDTO> codeDTO = codeQueryService.findByIdCheckEvent(id);
        return ResponseUtil.wrapOrNotFound(codeDTO);
    }

    /**
     * {@code DELETE  /codes/:id} : delete the "id" code.
     *
     * @param id the id of the codeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/codes/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        log.debug("REST request to delete Code : {}", id);
        codeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
