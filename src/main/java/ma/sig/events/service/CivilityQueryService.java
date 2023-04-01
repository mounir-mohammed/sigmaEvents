package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Civility;
import ma.sig.events.repository.CivilityRepository;
import ma.sig.events.service.criteria.CivilityCriteria;
import ma.sig.events.service.dto.CivilityDTO;
import ma.sig.events.service.mapper.CivilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Civility} entities in the database.
 * The main input is a {@link CivilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CivilityDTO} or a {@link Page} of {@link CivilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CivilityQueryService extends QueryService<Civility> {

    private final Logger log = LoggerFactory.getLogger(CivilityQueryService.class);

    private final CivilityRepository civilityRepository;

    private final CivilityMapper civilityMapper;

    public CivilityQueryService(CivilityRepository civilityRepository, CivilityMapper civilityMapper) {
        this.civilityRepository = civilityRepository;
        this.civilityMapper = civilityMapper;
    }

    /**
     * Return a {@link List} of {@link CivilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CivilityDTO> findByCriteria(CivilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Civility> specification = createSpecification(criteria);
        return civilityMapper.toDto(civilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CivilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CivilityDTO> findByCriteria(CivilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Civility> specification = createSpecification(criteria);
        return civilityRepository.findAll(specification, page).map(civilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CivilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Civility> specification = createSpecification(criteria);
        return civilityRepository.count(specification);
    }

    /**
     * Function to convert {@link CivilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Civility> createSpecification(CivilityCriteria criteria) {
        Specification<Civility> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getCivilityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCivilityId(), Civility_.civilityId));
            }
            if (criteria.getCivilityValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCivilityValue(), Civility_.civilityValue));
            }
            if (criteria.getCivilityDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCivilityDescription(), Civility_.civilityDescription));
            }
            if (criteria.getCivilityCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCivilityCode(), Civility_.civilityCode));
            }
            if (criteria.getCivilityParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCivilityParams(), Civility_.civilityParams));
            }
            if (criteria.getCivilityAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCivilityAttributs(), Civility_.civilityAttributs));
            }
            if (criteria.getCivilityStat() != null) {
                specification = specification.and(buildSpecification(criteria.getCivilityStat(), Civility_.civilityStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Civility_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}
