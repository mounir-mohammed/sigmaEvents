package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.AuthentificationType;
import ma.sig.events.repository.AuthentificationTypeRepository;
import ma.sig.events.service.criteria.AuthentificationTypeCriteria;
import ma.sig.events.service.dto.AuthentificationTypeDTO;
import ma.sig.events.service.mapper.AuthentificationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AuthentificationType} entities in the database.
 * The main input is a {@link AuthentificationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuthentificationTypeDTO} or a {@link Page} of {@link AuthentificationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuthentificationTypeQueryService extends QueryService<AuthentificationType> {

    private final Logger log = LoggerFactory.getLogger(AuthentificationTypeQueryService.class);

    private final AuthentificationTypeRepository authentificationTypeRepository;

    private final AuthentificationTypeMapper authentificationTypeMapper;

    public AuthentificationTypeQueryService(
        AuthentificationTypeRepository authentificationTypeRepository,
        AuthentificationTypeMapper authentificationTypeMapper
    ) {
        this.authentificationTypeRepository = authentificationTypeRepository;
        this.authentificationTypeMapper = authentificationTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AuthentificationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthentificationTypeDTO> findByCriteria(AuthentificationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuthentificationType> specification = createSpecification(criteria);
        return authentificationTypeMapper.toDto(authentificationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuthentificationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthentificationTypeDTO> findByCriteria(AuthentificationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuthentificationType> specification = createSpecification(criteria);
        return authentificationTypeRepository.findAll(specification, page).map(authentificationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthentificationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuthentificationType> specification = createSpecification(criteria);
        return authentificationTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link AuthentificationTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuthentificationType> createSpecification(AuthentificationTypeCriteria criteria) {
        Specification<AuthentificationType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getAuthentificationTypeId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAuthentificationTypeId(), AuthentificationType_.authentificationTypeId)
                    );
            }
            if (criteria.getAuthentificationTypeValue() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAuthentificationTypeValue(), AuthentificationType_.authentificationTypeValue)
                    );
            }
            if (criteria.getAuthentificationTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getAuthentificationTypeDescription(),
                            AuthentificationType_.authentificationTypeDescription
                        )
                    );
            }
            if (criteria.getAuthentificationTypeParams() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAuthentificationTypeParams(), AuthentificationType_.authentificationTypeParams)
                    );
            }
            if (criteria.getAuthentificationTypeAttributs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getAuthentificationTypeAttributs(),
                            AuthentificationType_.authentificationTypeAttributs
                        )
                    );
            }
            if (criteria.getAuthentificationTypeStat() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAuthentificationTypeStat(), AuthentificationType_.authentificationTypeStat)
                    );
            }
        }
        return specification;
    }
}
