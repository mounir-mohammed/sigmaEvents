package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Cloning;
import ma.sig.events.repository.CloningRepository;
import ma.sig.events.service.criteria.CloningCriteria;
import ma.sig.events.service.dto.CloningDTO;
import ma.sig.events.service.mapper.CloningMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Cloning} entities in the database.
 * The main input is a {@link CloningCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CloningDTO} or a {@link Page} of {@link CloningDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CloningQueryService extends QueryService<Cloning> {

    private final Logger log = LoggerFactory.getLogger(CloningQueryService.class);

    private final CloningRepository cloningRepository;

    private final CloningMapper cloningMapper;

    public CloningQueryService(CloningRepository cloningRepository, CloningMapper cloningMapper) {
        this.cloningRepository = cloningRepository;
        this.cloningMapper = cloningMapper;
    }

    /**
     * Return a {@link List} of {@link CloningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CloningDTO> findByCriteria(CloningCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cloning> specification = createSpecification(criteria);
        return cloningMapper.toDto(cloningRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CloningDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CloningDTO> findByCriteria(CloningCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cloning> specification = createSpecification(criteria);
        return cloningRepository.findAll(specification, page).map(cloningMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CloningCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cloning> specification = createSpecification(criteria);
        return cloningRepository.count(specification);
    }

    /**
     * Function to convert {@link CloningCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cloning> createSpecification(CloningCriteria criteria) {
        Specification<Cloning> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCloningId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloningId(), Cloning_.cloningId));
            }
            if (criteria.getCloningDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCloningDescription(), Cloning_.cloningDescription));
            }
            if (criteria.getCloningOldEventId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloningOldEventId(), Cloning_.cloningOldEventId));
            }
            if (criteria.getCloningNewEventId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloningNewEventId(), Cloning_.cloningNewEventId));
            }
            if (criteria.getCloningUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloningUserId(), Cloning_.cloningUserId));
            }
            if (criteria.getCloningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloningDate(), Cloning_.cloningDate));
            }
            if (criteria.getClonedEntitys() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClonedEntitys(), Cloning_.clonedEntitys));
            }
            if (criteria.getClonedParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClonedParams(), Cloning_.clonedParams));
            }
            if (criteria.getClonedAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClonedAttributs(), Cloning_.clonedAttributs));
            }
            if (criteria.getClonedStat() != null) {
                specification = specification.and(buildSpecification(criteria.getClonedStat(), Cloning_.clonedStat));
            }
        }
        return specification;
    }
}
