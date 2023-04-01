package ma.sig.events.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.sig.events.repository.CodeTypeRepository;
import ma.sig.events.service.CodeTypeQueryService;
import ma.sig.events.service.CodeTypeService;
import ma.sig.events.service.criteria.CodeTypeCriteria;
import ma.sig.events.service.dto.CodeTypeDTO;
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
 * REST controller for managing {@link ma.sig.events.domain.CodeType}.
 */
@RestController
@RequestMapping("/api")
public class CodeTypeResource {

    private final Logger log = LoggerFactory.getLogger(CodeTypeResource.class);

    private static final String ENTITY_NAME = "codeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeTypeService codeTypeService;

    private final CodeTypeRepository codeTypeRepository;

    private final CodeTypeQueryService codeTypeQueryService;

    public CodeTypeResource(
        CodeTypeService codeTypeService,
        CodeTypeRepository codeTypeRepository,
        CodeTypeQueryService codeTypeQueryService
    ) {
        this.codeTypeService = codeTypeService;
        this.codeTypeRepository = codeTypeRepository;
        this.codeTypeQueryService = codeTypeQueryService;
    }

    /**
     * {@code POST  /code-types} : Create a new codeType.
     *
     * @param codeTypeDTO the codeTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeTypeDTO, or with status {@code 400 (Bad Request)} if the codeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/code-types")
    public ResponseEntity<CodeTypeDTO> createCodeType(@Valid @RequestBody CodeTypeDTO codeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CodeType : {}", codeTypeDTO);
        if (codeTypeDTO.getCodeTypeId() != null) {
            throw new BadRequestAlertException("A new codeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeTypeDTO result = codeTypeService.save(codeTypeDTO);
        return ResponseEntity
            .created(new URI("/api/code-types/" + result.getCodeTypeId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCodeTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /code-types/:codeTypeId} : Updates an existing codeType.
     *
     * @param codeTypeId the id of the codeTypeDTO to save.
     * @param codeTypeDTO the codeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the codeTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/code-types/{codeTypeId}")
    public ResponseEntity<CodeTypeDTO> updateCodeType(
        @PathVariable(value = "codeTypeId", required = false) final Long codeTypeId,
        @Valid @RequestBody CodeTypeDTO codeTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CodeType : {}, {}", codeTypeId, codeTypeDTO);
        if (codeTypeDTO.getCodeTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codeTypeId, codeTypeDTO.getCodeTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeTypeRepository.existsById(codeTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CodeTypeDTO result = codeTypeService.update(codeTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeTypeDTO.getCodeTypeId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /code-types/:codeTypeId} : Partial updates given fields of an existing codeType, field will ignore if it is null
     *
     * @param codeTypeId the id of the codeTypeDTO to save.
     * @param codeTypeDTO the codeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the codeTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the codeTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the codeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/code-types/{codeTypeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodeTypeDTO> partialUpdateCodeType(
        @PathVariable(value = "codeTypeId", required = false) final Long codeTypeId,
        @NotNull @RequestBody CodeTypeDTO codeTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CodeType partially : {}, {}", codeTypeId, codeTypeDTO);
        if (codeTypeDTO.getCodeTypeId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codeTypeId, codeTypeDTO.getCodeTypeId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codeTypeRepository.existsById(codeTypeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodeTypeDTO> result = codeTypeService.partialUpdate(codeTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeTypeDTO.getCodeTypeId().toString())
        );
    }

    /**
     * {@code GET  /code-types} : get all the codeTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeTypes in body.
     */
    @GetMapping("/code-types")
    public ResponseEntity<List<CodeTypeDTO>> getAllCodeTypes(
        CodeTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CodeTypes by criteria: {}", criteria);
        Page<CodeTypeDTO> page = codeTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /code-types/count} : count all the codeTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/code-types/count")
    public ResponseEntity<Long> countCodeTypes(CodeTypeCriteria criteria) {
        log.debug("REST request to count CodeTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(codeTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /code-types/:id} : get the "id" codeType.
     *
     * @param id the id of the codeTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/code-types/{id}")
    public ResponseEntity<CodeTypeDTO> getCodeType(@PathVariable Long id) {
        log.debug("REST request to get CodeType : {}", id);
        Optional<CodeTypeDTO> codeTypeDTO = codeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeTypeDTO);
    }

    /**
     * {@code DELETE  /code-types/:id} : delete the "id" codeType.
     *
     * @param id the id of the codeTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/code-types/{id}")
    public ResponseEntity<Void> deleteCodeType(@PathVariable Long id) {
        log.debug("REST request to delete CodeType : {}", id);
        codeTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
