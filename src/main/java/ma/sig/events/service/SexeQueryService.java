package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.Sexe;
import ma.sig.events.repository.SexeRepository;
import ma.sig.events.service.criteria.SexeCriteria;
import ma.sig.events.service.dto.SexeDTO;
import ma.sig.events.service.mapper.SexeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Sexe} entities in the database.
 * The main input is a {@link SexeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SexeDTO} or a {@link Page} of {@link SexeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SexeQueryService extends QueryService<Sexe> {

    private final Logger log = LoggerFactory.getLogger(SexeQueryService.class);

    private final SexeRepository sexeRepository;

    private final SexeMapper sexeMapper;

    public SexeQueryService(SexeRepository sexeRepository, SexeMapper sexeMapper) {
        this.sexeRepository = sexeRepository;
        this.sexeMapper = sexeMapper;
    }

    /**
     * Return a {@link List} of {@link SexeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SexeDTO> findByCriteria(SexeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sexe> specification = createSpecification(criteria);
        return sexeMapper.toDto(sexeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SexeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SexeDTO> findByCriteria(SexeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sexe> specification = createSpecification(criteria);
        return sexeRepository.findAll(specification, page).map(sexeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SexeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sexe> specification = createSpecification(criteria);
        return sexeRepository.count(specification);
    }

    /**
     * Function to convert {@link SexeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Sexe> createSpecification(SexeCriteria criteria) {
        Specification<Sexe> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getSexeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSexeId(), Sexe_.sexeId));
            }
            if (criteria.getSexeValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexeValue(), Sexe_.sexeValue));
            }
            if (criteria.getSexeDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexeDescription(), Sexe_.sexeDescription));
            }
            if (criteria.getSexeTypeParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexeTypeParams(), Sexe_.sexeTypeParams));
            }
            if (criteria.getSexeTypeAttributs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexeTypeAttributs(), Sexe_.sexeTypeAttributs));
            }
            if (criteria.getSexeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getSexeStat(), Sexe_.sexeStat));
            }
            if (criteria.getAccreditationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccreditationId(),
                            root -> root.join(Sexe_.accreditations, JoinType.LEFT).get(Accreditation_.accreditationId)
                        )
                    );
            }
        }
        return specification;
    }
}
