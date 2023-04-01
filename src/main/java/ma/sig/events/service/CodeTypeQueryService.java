package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.CodeType;
import ma.sig.events.repository.CodeTypeRepository;
import ma.sig.events.service.criteria.CodeTypeCriteria;
import ma.sig.events.service.dto.CodeTypeDTO;
import ma.sig.events.service.mapper.CodeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CodeType} entities in the database.
 * The main input is a {@link CodeTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CodeTypeDTO} or a {@link Page} of {@link CodeTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CodeTypeQueryService extends QueryService<CodeType> {

    private final Logger log = LoggerFactory.getLogger(CodeTypeQueryService.class);

    private final CodeTypeRepository codeTypeRepository;

    private final CodeTypeMapper codeTypeMapper;

    public CodeTypeQueryService(CodeTypeRepository codeTypeRepository, CodeTypeMapper codeTypeMapper) {
        this.codeTypeRepository = codeTypeRepository;
        this.codeTypeMapper = codeTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CodeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CodeTypeDTO> findByCriteria(CodeTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CodeType> specification = createSpecification(criteria);
        return codeTypeMapper.toDto(codeTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CodeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CodeTypeDTO> findByCriteria(CodeTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CodeType> specification = createSpecification(criteria);
        return codeTypeRepository.findAll(specification, page).map(codeTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CodeTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CodeType> specification = createSpecification(criteria);
        return codeTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CodeTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CodeType> createSpecification(CodeTypeCriteria criteria) {
        Specification<CodeType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCodeTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeTypeId(), CodeType_.codeTypeId));
            }
            if (criteria.getCodeTypeValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeTypeValue(), CodeType_.codeTypeValue));
            }
            if (criteria.getCodeTypeDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCodeTypeDescription(), CodeType_.codeTypeDescription));
            }
            if (criteria.getCodeTypeParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeTypeParams(), CodeType_.codeTypeParams));
            }
            if (criteria.getCodeTypeAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeTypeAttributs(), CodeType_.codeTypeAttributs));
            }
            if (criteria.getCodeTypeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getCodeTypeStat(), CodeType_.codeTypeStat));
            }
            if (criteria.getCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCodeId(), root -> root.join(CodeType_.codes, JoinType.LEFT).get(Code_.codeId))
                    );
            }
        }
        return specification;
    }
}
