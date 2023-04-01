package ma.sig.events.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ma.sig.events.domain.*; // for static metamodels
import ma.sig.events.domain.InfoSuppType;
import ma.sig.events.repository.InfoSuppTypeRepository;
import ma.sig.events.service.criteria.InfoSuppTypeCriteria;
import ma.sig.events.service.dto.InfoSuppTypeDTO;
import ma.sig.events.service.mapper.InfoSuppTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InfoSuppType} entities in the database.
 * The main input is a {@link InfoSuppTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InfoSuppTypeDTO} or a {@link Page} of {@link InfoSuppTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InfoSuppTypeQueryService extends QueryService<InfoSuppType> {

    private final Logger log = LoggerFactory.getLogger(InfoSuppTypeQueryService.class);

    private final InfoSuppTypeRepository infoSuppTypeRepository;

    private final InfoSuppTypeMapper infoSuppTypeMapper;

    public InfoSuppTypeQueryService(InfoSuppTypeRepository infoSuppTypeRepository, InfoSuppTypeMapper infoSuppTypeMapper) {
        this.infoSuppTypeRepository = infoSuppTypeRepository;
        this.infoSuppTypeMapper = infoSuppTypeMapper;
    }

    /**
     * Return a {@link List} of {@link InfoSuppTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InfoSuppTypeDTO> findByCriteria(InfoSuppTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InfoSuppType> specification = createSpecification(criteria);
        return infoSuppTypeMapper.toDto(infoSuppTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InfoSuppTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InfoSuppTypeDTO> findByCriteria(InfoSuppTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InfoSuppType> specification = createSpecification(criteria);
        return infoSuppTypeRepository.findAll(specification, page).map(infoSuppTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InfoSuppTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InfoSuppType> specification = createSpecification(criteria);
        return infoSuppTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link InfoSuppTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InfoSuppType> createSpecification(InfoSuppTypeCriteria criteria) {
        Specification<InfoSuppType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getInfoSuppTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInfoSuppTypeId(), InfoSuppType_.infoSuppTypeId));
            }
            if (criteria.getInfoSuppTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoSuppTypeName(), InfoSuppType_.infoSuppTypeName));
            }
            if (criteria.getInfoSuppTypeDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getInfoSuppTypeDescription(), InfoSuppType_.infoSuppTypeDescription)
                    );
            }
            if (criteria.getInfoSuppTypeParams() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInfoSuppTypeParams(), InfoSuppType_.infoSuppTypeParams));
            }
            if (criteria.getInfoSuppTypeAttributs() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInfoSuppTypeAttributs(), InfoSuppType_.infoSuppTypeAttributs));
            }
            if (criteria.getInfoSuppTypeStat() != null) {
                specification = specification.and(buildSpecification(criteria.getInfoSuppTypeStat(), InfoSuppType_.infoSuppTypeStat));
            }
            if (criteria.getInfoSuppId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInfoSuppId(),
                            root -> root.join(InfoSuppType_.infoSupps, JoinType.LEFT).get(InfoSupp_.infoSuppId)
                        )
                    );
            }
        }
        return specification;
    }
}
