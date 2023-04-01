package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.OperationType;
import ma.sig.events.repository.OperationTypeRepository;
import ma.sig.events.service.criteria.OperationTypeCriteria;
import ma.sig.events.service.dto.OperationTypeDTO;
import ma.sig.events.service.mapper.OperationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OperationType} entities in the database.
 * The main input is a {@link OperationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperationTypeDTO} or a {@link Page} of {@link OperationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationTypeQueryService extends QueryService<OperationType> {

    private final Logger log = LoggerFactory.getLogger(OperationTypeQueryService.class);

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeMapper operationTypeMapper;

    public OperationTypeQueryService(OperationTypeRepository operationTypeRepository, OperationTypeMapper operationTypeMapper) {
        this.operationTypeRepository = operationTypeRepository;
        this.operationTypeMapper = operationTypeMapper;
    }

    /**
     * Return a {@link List} of {@link OperationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperationTypeDTO> findByCriteria(OperationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OperationType> specification = createSpecification(criteria);
        return operationTypeMapper.toDto(operationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OperationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationTypeDTO> findByCriteria(OperationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OperationType> specification = createSpecification(criteria);
        return operationTypeRepository.findAll(specification, page).map(operationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OperationType> specification = createSpecification(criteria);
        return operationTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link OperationTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OperationType> createSpecification(OperationTypeCriteria criteria) {
        Specification<OperationType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getOperationTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperationTypeId(), OperationType_.operationTypeId));
            }
            if (criteria.getOperationTypeValue() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOperationTypeValue(), OperationType_.operationTypeValue));
            }
            if (criteria.getOperationTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationTypeDescription(), OperationType_.operationTypeDescription)
                    );
            }
            if (criteria.getOperationTypeParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOperationTypeParams(), OperationType_.operationTypeParams));
            }
            if (criteria.getOperationTypeAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOperationTypeAttributs(), OperationType_.operationTypeAttributs)
                    );
            }
            if (criteria.getOperationTypeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getOperationTypeStat(), OperationType_.operationTypeStat));
            }
            if (criteria.getOperationHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOperationHistoryId(),
                            root -> root.join(OperationType_.operationHistories, JoinType.LEFT).get(OperationHistory_.operationHistoryId)
                        )
                    );
            }
        }
        return specification;
    }
}
