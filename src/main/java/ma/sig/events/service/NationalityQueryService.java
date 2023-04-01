package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Nationality;
import ma.sig.events.repository.NationalityRepository;
import ma.sig.events.service.criteria.NationalityCriteria;
import ma.sig.events.service.dto.NationalityDTO;
import ma.sig.events.service.mapper.NationalityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Nationality} entities in the database.
 * The main input is a {@link NationalityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NationalityDTO} or a {@link Page} of {@link NationalityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NationalityQueryService extends QueryService<Nationality> {

    private final Logger log = LoggerFactory.getLogger(NationalityQueryService.class);

    private final NationalityRepository nationalityRepository;

    private final NationalityMapper nationalityMapper;

    public NationalityQueryService(NationalityRepository nationalityRepository, NationalityMapper nationalityMapper) {
        this.nationalityRepository = nationalityRepository;
        this.nationalityMapper = nationalityMapper;
    }

    /**
     * Return a {@link List} of {@link NationalityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NationalityDTO> findByCriteria(NationalityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nationality> specification = createSpecification(criteria);
        return nationalityMapper.toDto(nationalityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NationalityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NationalityDTO> findByCriteria(NationalityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nationality> specification = createSpecification(criteria);
        return nationalityRepository.findAll(specification, page).map(nationalityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NationalityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nationality> specification = createSpecification(criteria);
        return nationalityRepository.count(specification);
    }

    /**
     * Function to convert {@link NationalityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nationality> createSpecification(NationalityCriteria criteria) {
        Specification<Nationality> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getNationalityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNationalityId(), Nationality_.nationalityId));
            }
            if (criteria.getNationalityValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalityValue(), Nationality_.nationalityValue));
            }
            if (criteria.getNationalityAbreviation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNationalityAbreviation(), Nationality_.nationalityAbreviation));
            }
            if (criteria.getNationalityDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNationalityDescription(), Nationality_.nationalityDescription));
            }
            if (criteria.getNationalityParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNationalityParams(), Nationality_.nationalityParams));
            }
            if (criteria.getNationalityAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNationalityAttributs(), Nationality_.nationalityAttributs));
            }
            if (criteria.getNationalityStat() != null) {
                specification = specification.and(buildSpecification(criteria.getNationalityStat(), Nationality_.nationalityStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Nationality_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}
