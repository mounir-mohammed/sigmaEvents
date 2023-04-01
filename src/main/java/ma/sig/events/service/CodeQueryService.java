package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Code;
import ma.sig.events.repository.CodeRepository;
import ma.sig.events.service.criteria.CodeCriteria;
import ma.sig.events.service.dto.CodeDTO;
import ma.sig.events.service.mapper.CodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Code} entities in the database.
 * The main input is a {@link CodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CodeDTO} or a {@link Page} of {@link CodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CodeQueryService extends QueryService<Code> {

    private final Logger log = LoggerFactory.getLogger(CodeQueryService.class);

    private final CodeRepository codeRepository;

    private final CodeMapper codeMapper;

    public CodeQueryService(CodeRepository codeRepository, CodeMapper codeMapper) {
        this.codeRepository = codeRepository;
        this.codeMapper = codeMapper;
    }

    /**
     * Return a {@link List} of {@link CodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CodeDTO> findByCriteria(CodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Code> specification = createSpecification(criteria);
        return codeMapper.toDto(codeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CodeDTO> findByCriteria(CodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Code> specification = createSpecification(criteria);
        return codeRepository.findAll(specification, page).map(codeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Code> specification = createSpecification(criteria);
        return codeRepository.count(specification);
    }

    /**
     * Function to convert {@link CodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Code> createSpecification(CodeCriteria criteria) {
        Specification<Code> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCodeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeId(), Code_.codeId));
            }
            if (criteria.getCodeForEntity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeForEntity(), Code_.codeForEntity));
            }
            if (criteria.getCodeEntityValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeEntityValue(), Code_.codeEntityValue));
            }
            if (criteria.getCodeValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeValue(), Code_.codeValue));
            }
            if (criteria.getCodeUsed() != null) {
                specification = specification.and(buildSpecification(criteria.getCodeUsed(), Code_.codeUsed));
            }
            if (criteria.getCodeParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeParams(), Code_.codeParams));
            }
            if (criteria.getCodeAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeAttributs(), Code_.codeAttributs));
            }
            if (criteria.getCodeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getCodeStat(), Code_.codeStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Code_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
            if (criteria.getCodeTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCodeTypeId(),
                            root -> root.join(Code_.codeType, JoinType.LEFT).get(CodeType_.codeTypeId)
                        )
                    );
            }
            if (criteria.getEventId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEventId(), root -> root.join(Code_.event, JoinType.LEFT).get(Event_.eventId))
                    );
            }
        }
        return specification;
    }
}
